package com.shareyi.molicode.common.chain;

import com.google.common.collect.Lists;
import com.shareyi.molicode.common.chain.handler.Handler;
import com.shareyi.molicode.common.chain.handler.HandlerAware;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * handler chain 生成工厂
 *
 * @author zhangshibin
 * @date 2018/8/7
 */
public class HandlerChainFactoryImpl implements ApplicationContextAware {


    static Logger LOGGER = LoggerFactory.getLogger(HandlerChainFactoryImpl.class);

    private ApplicationContext applicationContext;

    private ConcurrentHashMap<String, List<Handler>> cacheMap = new ConcurrentHashMap();

    private static HandlerChainFactoryImpl instance;


    /**
     * 通过注解模式获取责任链，避免配置文件
     * 适合单一使用，不复用的责任链
     *
     * @param clazz
     * @return
     */
    public List<Handler> createByHandlerAware(Class<? extends HandlerAware> clazz) {
        List<Handler> handlerList = cacheMap.get(clazz.getName());
        if (handlerList != null) {
            LOGGER.debug("缓存中获取到责任链列表,size={}, awareClass={}", handlerList.size(), clazz);
            return handlerList;
        }
        List<HandlerAware> handlerAwareList = getHandlerListFromSpringContext(clazz);
        handlerList = (List) handlerAwareList;
        cacheMap.put(clazz.getName(), handlerList);
        return handlerList;
    }

    /**
     * 从spring CONTEXT 中获取
     *
     * @param clazz
     * @return
     */
    private List<HandlerAware> getHandlerListFromSpringContext(Class<? extends HandlerAware> clazz) {
        Map<String, ? extends HandlerAware> beanMap = applicationContext.getBeansOfType(clazz, true, false);
        if (MapUtils.isEmpty(beanMap)) {
            LOGGER.warn("未查询到责任链数据, awareClass={}", clazz);
            return new ArrayList(0);
        }

        List<HandlerAware> handlerAwareList = Lists.newArrayListWithCapacity(beanMap.size());
        for (Map.Entry<String, ? extends HandlerAware> handlerAwareEntry : beanMap.entrySet()) {
            HandlerAware aware = handlerAwareEntry.getValue();
            if (aware instanceof Handler) {
                handlerAwareList.add(aware);
            } else {
                LOGGER.warn("{} 未实现handler接口, clazz={}, awareClass={}", handlerAwareEntry.getKey(),
                        aware.getClass(), clazz);
            }
        }
        //正序排列，序号小的排在前面
        Collections.sort(handlerAwareList, new Comparator<HandlerAware>() {
            @Override
            public int compare(HandlerAware o1, HandlerAware o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        LOGGER.debug("从spring context查询到责任链, awareClass={}，size={}", clazz, handlerAwareList.size());
        return handlerAwareList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public static HandlerChainFactoryImpl getInstance() {
        if (instance == null) {
            instance = new HandlerChainFactoryImpl();
        }
        return instance;
    }

    /**
     * 执行责任链
     *
     * @param clazz
     * @param context
     */
    public static <T> void executeByHandlerAware(Class<? extends HandlerAware> clazz, T context) {
        List<Handler> handlerList = getInstance().createByHandlerAware(clazz);
        HandlerChain<T> handlerChain = new DefaultHandlerChain(handlerList);
        HandlerChainExecutor.executeSilent(context, handlerChain);
    }

}
