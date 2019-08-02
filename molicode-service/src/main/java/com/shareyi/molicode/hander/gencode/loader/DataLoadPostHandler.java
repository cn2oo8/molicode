package com.shareyi.molicode.hander.gencode.loader;

import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.DataLoadHandlerAware;
import com.shareyi.molicode.common.constants.ConfigKeyConstant;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.context.MoliCodeContext;
import com.shareyi.molicode.common.enums.TemplateTypeEnum;
import com.shareyi.molicode.common.utils.ValidateUtils;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.vo.code.AutoCodeParams;
import com.shareyi.molicode.common.vo.code.AutoMakeVo;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 数据加载后处理器
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Service
public class DataLoadPostHandler extends SimpleHandler<MoliCodeContext> implements DataLoadHandlerAware {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public boolean shouldHandle(MoliCodeContext context) {
        return true;
    }

    @Override
    public void doHandle(MoliCodeContext context) {
        AutoCodeParams autoCodeParams = context.getAutoCodeParams();
        AutoMakeVo autoMakeVo = (AutoMakeVo) context.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
        if (autoMakeVo != null && autoCodeParams.getProjectOutputDir() != null) {
            autoMakeVo.setProjectOutputDir(autoCodeParams.getProjectOutputDir());
        }
        if (Objects.equals(autoCodeParams.getTemplateType(), TemplateTypeEnum.LOCAL.getCode())) {
            Validate.notEmpty(autoCodeParams.getAutoXmlPath(), "autoXmlPath不能为空");
            Validate.notEmpty(autoCodeParams.getTemplateBaseDir(), "模板基础目录不能为空");
        } else if (Objects.equals(autoCodeParams.getTemplateType(), TemplateTypeEnum.MAVEN.getCode())) {
            ValidateUtils.notEmptyField(autoCodeParams.getMavenResourceVo(), ConfigKeyConstant.MavenConfig.GROUP_ID);
            ValidateUtils.notEmptyField(autoCodeParams.getMavenResourceVo(), ConfigKeyConstant.MavenConfig.VERSION);
            ValidateUtils.notEmptyField(autoCodeParams.getMavenResourceVo(), ConfigKeyConstant.MavenConfig.ARTIFACT_ID);
            ValidateUtils.notEmptyField(autoCodeParams.getMavenResourceVo(), ConfigKeyConstant.GlobalMavenConfig.LOCAL_REPOSITORY);
        }
    }
}
