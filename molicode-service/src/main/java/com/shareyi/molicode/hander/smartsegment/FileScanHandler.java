package com.shareyi.molicode.hander.smartsegment;

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentUnitHandlerAware;
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentHandlerAware;
import com.shareyi.molicode.common.context.SmartSegmentContext;
import com.shareyi.molicode.common.context.SmartSegmentUnitContext;
import com.shareyi.molicode.common.filter.FileNameExpressionFilter;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * 文件扫描器
 *
 * @author zhangshibin
 * @date 2019/5/19
 */
@Service
public class FileScanHandler extends SimpleHandler<SmartSegmentContext> implements SmartSegmentHandlerAware {


    @Override
    public int getOrder() {
        return 2;
    }


    @Override
    public boolean shouldHandle(SmartSegmentContext smartSegmentContext) {
        return true;
    }

    @Override
    public void doHandle(SmartSegmentContext smartSegmentContext) {
        SmartSegmentPageVo pageVo = smartSegmentContext.getSegmentPageVo();
        String srcPath = pageVo.getSrcPath();
        Validate.notEmpty(srcPath, "源路径不能为空");

        File file = new File(srcPath);
        Validate.assertTrue(file.exists(), "文件不存在,path=" + srcPath);

        LogHelper.FRONT_CONSOLE.info("开始从入口文件进行扫描：{}", srcPath);

        FileNameExpressionFilter whiteListFilter = new FileNameExpressionFilter(pageVo.getWhiteListExp());
        FileNameExpressionFilter ignoreFilter = new FileNameExpressionFilter(pageVo.getIgnoreExp());
        whiteListFilter.setEmptyMatch(true);
        ignoreFilter.setEmptyMatch(false);

        smartSegmentContext.setWhiteListFilter(whiteListFilter);
        smartSegmentContext.setIgnoreFilter(ignoreFilter);

        fileRecurse(file, smartSegmentContext);
    }

    /**
     * 遍历文件夹，过滤出来需要处理的文件进行处理
     *
     * @param file
     */
    private void fileRecurse(File file, SmartSegmentContext smartSegmentContext) {
        //如果文件命中忽略表达式
        if (smartSegmentContext.getIgnoreFilter().isMatch(file.getName())) {
            return;
        }
        if (file.isFile()) {
            //白名单只针对文件
            if (!smartSegmentContext.getWhiteListFilter().isMatch(file.getName())) {
                return;
            }

            //对过滤出来的文件进行具体处理
            this.dealMatchNameFile(file, smartSegmentContext);

        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                fileRecurse(subFile, smartSegmentContext);
            }
        }
    }

    /**
     * 处理满足文件名称的文件
     *
     * @param file
     * @param smartSegmentContext
     */
    private void dealMatchNameFile(File file, SmartSegmentContext smartSegmentContext) {
        SmartSegmentUnitContext unitContext = SmartSegmentUnitContext.create(file, smartSegmentContext);
        HandlerChainFactoryImpl.executeByHandlerAware(SmartSegmentUnitHandlerAware.class, unitContext);
    }
}
