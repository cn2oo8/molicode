package com.shareyi.molicode.hander.gencode.loader;

import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataLoadHandlerAware;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.enums.ResourceTypeEnum;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.AutoCodeException;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.utils.Profiles;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 文件内容加载器
 *
 * @author david
 * @date 2018/10/3
 */
@Service
public class FileLoadHandler extends SimpleHandler<MoliCodeContext> implements DataLoadHandlerAware {

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        AutoCodeParams autoCodeParams = context.getAutoCodeParams();

        ResourceTypeEnum resourceTypeEnum = ResourceTypeEnum.Parser.parseToNullSafe(ResourceTypeEnum.class, autoCodeParams.getResourceType(), ResourceTypeEnum.FILE);
        switch (resourceTypeEnum) {
            case FILE:
            case DATABASE:
                this.loadFileContent(context, autoCodeParams);
                break;
            case FRONT:
                this.loadFrontContent(context, autoCodeParams);
                break;
            default:
                throw new AutoCodeException("不合法或者不支持的resourceType=" + autoCodeParams.getResourceType(), ResultCodeEnum.PARAM_ERROR);
        }
    }

    /**
     * 从前台窗口中获取数据
     *
     * @param context
     * @param autoCodeParams
     */
    private void loadFrontContent(MoliCodeContext context, AutoCodeParams autoCodeParams) {
        context.put(MoliCodeConstant.CTX_KEY_SRC_CONTENT, autoCodeParams.getFrontContent());
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param autoCodeParams
     */
    private void loadFileContent(MoliCodeContext context, AutoCodeParams autoCodeParams) {
        File file = new File(autoCodeParams.getTableModelPath());
        if (!file.exists() || !file.isFile()) {
            throw new AutoCodeException("文件不存在，path=" + autoCodeParams.getTableModelPath(), ResultCodeEnum.PARAM_ERROR);
        }

        if (!file.canRead()) {
            throw new AutoCodeException("文件不可读取，path=" + autoCodeParams.getTableModelPath(), ResultCodeEnum.PARAM_ERROR);
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String srcContent = IOUtils.toString(inputStream, Profiles.getInstance().getFileEncoding());
            context.put(MoliCodeConstant.CTX_KEY_SRC_CONTENT, srcContent);
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("读取文件内容失败, path={}", autoCodeParams.getTableModelPath(), e);
            throw new AutoCodeException("读取文件内容失败，path=" + autoCodeParams.getTableModelPath(), ResultCodeEnum.EXCEPTION);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
