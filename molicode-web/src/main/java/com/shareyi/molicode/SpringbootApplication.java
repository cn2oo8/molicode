package com.shareyi.molicode;

import com.shareyi.molicode.common.enums.BrowserWindowEnum;
import com.shareyi.molicode.common.gui.GuiWindowFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Objects;

/**
 * springboot启动入口类
 *
 * @author zhangshibin
 * @date 2018-09-23
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.shareyi.molicode.dao")
public class SpringbootApplication {

    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringbootApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = builder.headless(false).web(true).run(args);
        ConfigurableEnvironment configurableEnvironment = configurableApplicationContext.getEnvironment();
        final String port = configurableEnvironment.getProperty("server.port", "8086");
        final String windowName = configurableEnvironment.getProperty("browser.windowName", BrowserWindowEnum.SWING.getCode());

        //headless状态下，无开启窗口
        if(!Objects.equals(windowName, BrowserWindowEnum.HEADLESS.getCode())){
            final String url = "http://127.0.0.1:" + port + "/index.html?t=" + System.currentTimeMillis();
            GuiWindowFactory.getInstance().createWindow(windowName).initAndOpen(url);
        }
    }
}
