package com.shareyi.molicode.configuaration;

import com.shareyi.molicode.common.spring.StringToDateConverter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 描述
 *
 * @author david
 * @date 2018/8/23
 */
@Configuration
public class MyWebAppConfiguration   {

    @Resource
   RequestMappingHandlerAdapter adapter;

   @PostConstruct
    public void initEditableValidation(){
       ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) adapter.getWebBindingInitializer();
       if(initializer.getConversionService()!=null){
           GenericConversionService conversionService = (GenericConversionService) initializer.getConversionService();
           conversionService.addConverter(new StringToDateConverter());
       }
   }
}
