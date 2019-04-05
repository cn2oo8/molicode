package com.shareyi.molicode;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * <p>
 * 分两种profiles，对应的配置文件都在类路径的根目录下：
 * <ul>
 * <li>prod  生产环境，对应的配置文件有<code>important.properties, common.properties</code>，
 * 如果两个文件中的key相同，后面的属性文件中定义的值会覆盖前面的</li>
 * <li>dev 开发环境，对应的配置文件是<code>dev.properties </code></li>
 * </ul>
 * 默认的profile是<code>prod</code>，
 * 在开发环境可以使用SpringBoot支持的多种方式来覆盖这个值，比如用VM参数：<code>-Dspring.profiles.active=dev</code>
 * </p>
 * <p>
 * <p>
 * 每个人想在本地个性化定义一些属性，比如jsf的别名定义成和自己的用户名相关的，
 * 那可以在<code>dev-local.properties</code>文件中定义，它会覆盖<code>dev.properties</code>中定义的值。
 * </p>
 * <p>
 * <p>
 * <code>important.properties, common.properties和dev-local.properties</code>都不会被添加到git中。
 * </p>
 * <p>
 * <p>
 * @author david
 * @date 2018-08-21
 */
@Configuration
public class DefaultConfiguration {

    @Configuration
    @Profile("development")
    @PropertySource({"classpath:props/development.properties"})
    static class Development {
    }

    @Configuration
    @Profile("test")
    @PropertySource({"classpath:props/test.properties"})
    static class Test {
    }


    @Configuration
    @Profile("production")
    @PropertySource({"classpath:props/prod.properties", "classpath:important.properties"})
    static class Production {
    }
}