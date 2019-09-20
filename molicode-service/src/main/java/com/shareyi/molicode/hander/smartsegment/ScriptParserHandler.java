package com.shareyi.molicode.hander.smartsegment;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shareyi.molicode.common.chain.handler.SimpleHandler;
import com.shareyi.molicode.common.chain.handler.awares.SmartSegmentHandlerAware;
import com.shareyi.molicode.common.context.SmartSegmentContext;
import com.shareyi.molicode.common.enums.ResultCodeEnum;
import com.shareyi.molicode.common.exception.DefaultExceptionMaker;
import com.shareyi.molicode.common.utils.*;
import com.shareyi.molicode.common.valid.Validate;
import com.shareyi.molicode.common.vo.page.SmartSegmentPageVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 脚本加载器
 *
 * @author david
 * @date 2019/5/19
 */
@Service
public class ScriptParserHandler extends SimpleHandler<SmartSegmentContext> implements SmartSegmentHandlerAware {

    @Resource
    private TemplateUtil templateUtil;

    @Override
    public int getOrder() {
        return 1;
    }


    @Override
    public boolean shouldHandle(SmartSegmentContext smartSegmentContext) {
        return true;
    }

    @Override
    public void doHandle(SmartSegmentContext smartSegmentContext) {
        SmartSegmentPageVo pageVo = smartSegmentContext.getSegmentPageVo();
        String scriptPath = pageVo.getSegmentScriptPath();
        Validate.notEmpty(scriptPath, "脚本路径不能为空");

        File file = new File(scriptPath);
        Validate.assertTrue(file.exists(), "文件不存在,scriptPath=" + scriptPath);
        Validate.assertTrue(file.isFile() && file.canRead(), "非可读文件,scriptPath=" + scriptPath);
        String scriptContent;
        try {
            scriptContent = FileUtils.readFileToString(file, Profiles.getInstance().getFileEncoding());
        } catch (IOException e) {
            throw DefaultExceptionMaker.buildException("读取文件内容异常, path=" + file.getAbsolutePath(), ResultCodeEnum.EXCEPTION);
        }
        Validate.notEmpty(scriptContent, "scriptContent不能为空");


        Map<String, Object> scriptContext = new HashMap<>();
        Map<String, Object> evalTempContext = new HashMap<>();
        evalTempContext.put("StringUtils", StringUtils.class);
        evalTempContext.put("CollectionUtils", CollectionUtils.class);
        evalTempContext.put("JSON", JSON.class);
        evalTempContext.put("PubUtils", PubUtils.class);
        evalTempContext.put("tableNameUtil", new TableNameUtil());
        evalTempContext.put("scriptContext", scriptContext);
        //如果json配置不为空，需要将json配置设置其中
        if (StringUtils.isNotBlank(smartSegmentContext.getSegmentPageVo().getJsonConfig())) {
            evalTempContext.put("jsonConfig", JSON.parseObject(smartSegmentContext.getSegmentPageVo().getJsonConfig()));
        } else {
            evalTempContext.put("jsonConfig", Maps.newHashMap());
        }
        String output = templateUtil.renderContent(scriptContent, evalTempContext);
        LogHelper.FRONT_CONSOLE.info("解析脚本输出：{}", output);


        List<String> mustContainScriptKeyList = Lists.newArrayList("segmentStartFinder", "segmentEndFinder", "segmentProcess");
        for (String key : mustContainScriptKeyList) {
            if (!scriptContext.containsKey(key)) {
                throw DefaultExceptionMaker.buildException("脚本文件缺失实现：" + key, ResultCodeEnum.PARAM_ERROR);
            }
        }
        smartSegmentContext.setScriptContext(scriptContext);
    }
}
