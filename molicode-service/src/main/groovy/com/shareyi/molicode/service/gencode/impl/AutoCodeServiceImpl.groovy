package com.shareyi.molicode.service.gencode.impl

import com.google.common.collect.Lists
import com.shareyi.molicode.common.chain.HandlerChainExecutor
import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl
import com.shareyi.molicode.common.chain.handler.awares.CodeGenMainHandlerAware
import com.shareyi.molicode.common.constants.MoliCodeConstant
import com.shareyi.molicode.common.enums.EnumCode
import com.shareyi.molicode.common.enums.ResourceTypeEnum
import com.shareyi.molicode.common.utils.LogHelper
import com.shareyi.molicode.common.valid.Validate
import com.shareyi.molicode.common.vo.code.AutoCodeParams
import com.shareyi.molicode.common.vo.code.AutoMakeVo
import com.shareyi.molicode.common.web.CommonResult
import com.shareyi.molicode.common.context.MoliCodeContext
import com.shareyi.molicode.common.utils.ThreadLocalHolder
import com.shareyi.molicode.hander.gencode.loader.AutoMakeLoadHandler
import com.shareyi.molicode.service.conf.AcConfigService
import com.shareyi.molicode.service.conf.CommonExtInfoService
import com.shareyi.molicode.service.gencode.AutoCodeService
import com.shareyi.molicode.service.gencode.AutoMakeService
import com.shareyi.molicode.service.maven.MavenService
import org.springframework.stereotype.Service

import javax.annotation.Resource

@Service("autoCodeService")
class AutoCodeServiceImpl implements AutoCodeService {

    @Resource
    AcConfigService acConfigService

    @Resource
    MavenService mavenService;

    @Resource
    CommonExtInfoService commonExtInfoService;

    @Resource
    AutoMakeService autoMakeService;

    @Resource
    AutoMakeLoadHandler autoMakeLoadHandler;


    @Override
    CommonResult<String> generateCode(AutoCodeParams autoMakeParams) {
        CommonResult result = CommonResult.create();
        try {
            autoMakeParams.setLoadTemplateContent(true)
            ResourceTypeEnum resourceTypeEnum = EnumCode.Parser.parseToNullSafe(ResourceTypeEnum.class, autoMakeParams.resourceType, ResourceTypeEnum.FILE);
            if(resourceTypeEnum == ResourceTypeEnum.FILE){
                Validate.notEmpty(autoMakeParams.getTableModelPath(), "tableModelPath不能为空")
            }else{
                Validate.notEmpty(autoMakeParams.getFrontContent(), "frontContent不能为空")
            }
            Validate.notEmpty(autoMakeParams.getProjectOutputDir(), "代码输出目录不能为空")
            MoliCodeContext moliCodeContext = MoliCodeContext.create(autoMakeParams)
            ThreadLocalHolder.setMoliCodeContext(moliCodeContext)
            HandlerChainFactoryImpl.executeByHandlerAware(CodeGenMainHandlerAware.class, moliCodeContext)
            // String message = generateCodeInner(autoMakeParams);
            result.setResultInfo(true, moliCodeContext.getMessage().toString());
        } catch (Exception e) {
            LogHelper.FRONT_CONSOLE.error("生成代码失败，data={}", autoMakeParams, e);
            result.failed("生成代码失败，原因是：" + e.getMessage());
        }finally{
            ThreadLocalHolder.removeMoliCodeContext();
        }
        return result
    }
/**
 * 获取autoMake对象
 * @param autoXmlPath
 * @param templateBaseDir
 * @return
 */
    CommonResult<AutoMakeVo> getAutoMake(AutoCodeParams autoCodeParams) {
        CommonResult result = CommonResult.create();
        try {
            MoliCodeContext moliCodeContext = MoliCodeContext.create(autoCodeParams)
            HandlerChainExecutor.execute(moliCodeContext, Lists.newArrayList(autoMakeLoadHandler));
            AutoMakeVo autoMakeVo = (AutoMakeVo) moliCodeContext.get(MoliCodeConstant.CTX_KEY_AUTO_MAKE);
            result.addDefaultModel("autoMake", autoMakeVo);
            result.succeed();
        } catch (Exception e) {
            LogHelper.EXCEPTION.error("获取自动代码模型失败，autoMakeParams={}", autoCodeParams, e);
            result.failed("获取自动代码模型失败,原因是：" + e.getMessage());
        }
        return result;
    }

}
