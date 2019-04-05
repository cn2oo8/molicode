package com.shareyi.molicode.log;

import com.shareyi.molicode.common.constants.CommonConstant;
import com.shareyi.molicode.service.websocket.WebSocketServer;
import org.slf4j.MDC;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

@Plugin(name = "FrontConsoleAppender", category = "Core", elementType = "appender", printObject = true)
public class FrontConsoleAppender extends AbstractAppender {
    private String fileName;

    /* 构造函数 */
    public FrontConsoleAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, String fileName) {
        super(name, filter, layout, ignoreExceptions);
        this.fileName = fileName;
    }

    @Override
    public void append(LogEvent event) {
        final byte[] bytes = getLayout().toByteArray(event);
        String sid = (String) MDC.get(CommonConstant.SID);
        WebSocketServer.sendInfo(new String(bytes), sid);

    }

    /*  接收配置文件中的参数 */
    @PluginFactory
    public static FrontConsoleAppender createAppender(@PluginAttribute("name") String name,
                                                      @PluginAttribute("fileName") String fileName,
                                                      @PluginElement("Filter") final Filter filter,
                                                      @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                      @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("no name defined in conf.");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new FrontConsoleAppender(name, filter, layout, ignoreExceptions, fileName);
    }
}