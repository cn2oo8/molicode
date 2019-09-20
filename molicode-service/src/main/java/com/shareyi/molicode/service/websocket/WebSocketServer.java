package com.shareyi.molicode.service.websocket;

import com.shareyi.molicode.common.utils.LogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer implements Comparable<WebSocketServer> {

    static Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 会话ID
     */
    private String sid;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        this.sid = sid;
        addOnlineCount();           //在线数加1
        LOGGER.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
        try {
            sendMessage("日志链接连接成功");
        } catch (IOException e) {
            LOGGER.error("websocket IO异常", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        boolean removed = webSocketSet.remove(this);  //从set中删除
        if(removed){
            subOnlineCount();           //在线数减1
        }
        LOGGER.info("有一连接关闭, sid={}, 当前在线人数为{}", this.sid, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOGGER.info("收到来自窗口" + sid + "的信息:" + message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("发生错误, sid={}", sid, error);
        if (error instanceof EOFException) {
           this.onClose();
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, String sid) {
        //  LOGGER.info("推送消息到窗口" + sid + "，推送内容:" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                if (!item.session.isOpen()) {
                    continue;
                }
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (Objects.equals(item.sid, sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                LogHelper.EXCEPTION.error("发送信息失败", e);
                continue;
            }
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.incrementAndGet();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.decrementAndGet();
    }

    @Override
    public int compareTo(WebSocketServer o) {
        return this.sid.compareTo(o.sid);
    }
}

