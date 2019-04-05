package com.shareyi.molicode.configuaration;

import com.shareyi.molicode.common.chain.HandlerChainFactoryImpl;
import com.shareyi.molicode.common.gui.BrowserCallbackCenter;
import com.shareyi.molicode.common.utils.Profiles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 自定义bean 构建器
 *
 * @author zhangshibin
 * @date 2018/10/3
 */
@Configuration
public class BeanConfiguration {


    @Bean
    public HandlerChainFactoryImpl chainFactory() {
        return HandlerChainFactoryImpl.getInstance();
    }

    @Bean
    public Profiles createProfiles() {
        return Profiles.getInstance();
    }

}
