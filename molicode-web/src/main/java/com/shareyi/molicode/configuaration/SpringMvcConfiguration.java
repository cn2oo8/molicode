package com.shareyi.molicode.configuaration;

import com.shareyi.fileutil.FileUtil;
import com.shareyi.molicode.common.constants.MoliCodeConstant;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.interceptor.LogInterceptor;
import com.shareyi.molicode.interceptor.LoginInterceptor;
import com.shareyi.molicode.interceptor.PrivilegeInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * spring拦截器相关配置
 *
 * @author zhangshibin
 * @date 2018/11/4
 */
@SpringBootConfiguration
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {

    @Resource
    private LogInterceptor logInterceptor;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Resource
    private PrivilegeInterceptor privilegeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/loginfree/**", "/dist/**", "");
        registry.addInterceptor(privilegeInterceptor).addPathPatterns("/**").excludePathPatterns("/loginfree/**", "/dist/**", "");

    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String zipPath = FileUtil.getRuntimeFilePath(MoliCodeConstant.CODE_OUTPUT_ZIP_NAME);
        String location = "file:" + zipPath;
        LogHelper.DEFAULT.info("zip resourceLocations={}", location);
        registry.addResourceHandler("/zip/**").addResourceLocations(location);
        super.addResourceHandlers(registry);
    }
}
