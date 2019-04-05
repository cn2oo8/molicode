package com.shareyi.molicode.service.browser;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.shareyi.molicode.common.gui.BrowserCallbackCenter;
import com.shareyi.molicode.common.gui.BrowserCallbackListener;
import com.shareyi.molicode.common.utils.LogHelper;
import com.shareyi.molicode.common.web.CommonResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 浏览器回调中心服务实现
 *
 * @author zhangshibin
 * @date 2018/11/1
 */
@Service
public class BrowserCallbackCenterImpl implements BrowserCallbackCenter, BeanFactoryPostProcessor {

    /**
     * 命令回调列表上下文
     */
    Map<String, List<BrowserCallbackListener>> commandListenersContext = new ConcurrentHashMap<>();

    @Override
    public String callback(String action, String payload) {
        LogHelper.DEFAULT.info("action={}, payload={}", action, payload);
        CommonResult result = handleAction(action, payload);
        return JSON.toJSONString(result.getReturnMap());
    }

    /**
     * 处理action服务
     *
     * @param action
     * @param payload
     * @return
     */
    private CommonResult handleAction(String action, String payload) {
        List<BrowserCallbackListener> listeners = commandListenersContext.get(action);
        if (CollectionUtils.isEmpty(listeners)) {
            LogHelper.DEFAULT.warn("action={}未查询到监听器！", action);
            return CommonResult.create().failed("未查询到listener");
        }
        CommonResult firstResult = null;
        for (BrowserCallbackListener listener : listeners) {
            CommonResult result = listener.handle(payload);
            if (firstResult == null) {
                firstResult = result;
            }
        }
        return firstResult;
    }

    @Override
    public synchronized void addListener(BrowserCallbackListener listener) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(listener.getAction());
        List<BrowserCallbackListener> listeners = commandListenersContext.get(listener.getAction());
        if (listeners == null) {
            listeners = new ArrayList<>();
            commandListenersContext.put(listener.getAction(), listeners);
        }
        listeners.add(listener);
    }


    /**
     * 将所有的listener 注册到context 中，方便回调
     *
     * @param configurableListableBeanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        Map<String, BrowserCallbackListener> map = configurableListableBeanFactory.getBeansOfType(BrowserCallbackListener.class);
        if (MapUtils.isEmpty(map)) {
            return;
        }
        for (Map.Entry<String, BrowserCallbackListener> entry : map.entrySet()) {
            this.addListener(entry.getValue());
        }
    }
}
