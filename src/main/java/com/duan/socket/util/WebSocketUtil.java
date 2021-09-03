package com.duan.socket.util;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WebSocketUtil
 * @Author DuanJinFei
 * @Date 2021/4/25 14:50
 * @Version 1.0
 */
public final class WebSocketUtil {
    public static final Map<String, Session> LIVING_SESSION_CACHE = new ConcurrentHashMap<>();
    public static void sendMessageAll(String message){
        // 遍历全部缓存的session用户 并发送消息
        LIVING_SESSION_CACHE.forEach((sessionId,session)->sendMessage(session,message));
    }

    /**
     * 发送给指定用户消息
     * @param session
     * @param message
     */
    public static void sendMessage(Session session,String message) {
        if (session == null) return;
        final RemoteEndpoint.Basic basic = session.getBasicRemote(); // 获取服务端端点
        if (basic == null) return;
        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
