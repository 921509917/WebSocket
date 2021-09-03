package com.duan.socket.controller;

import com.duan.socket.util.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @ClassName ChatRoomServerEndPoint
 * @Author DuanJinFei
 * @Date 2021/4/25 15:01
 * @Version 1.0
 */
@RestController
@ServerEndpoint("/chat-room/{username}")
public class ChatRoomServerEndPoint {
    private static final Logger log = LoggerFactory.getLogger(ChatRoomServerEndPoint.class);

    @OnOpen
    public void openSession(@PathParam("username") String username, Session session){
        WebSocketUtil.LIVING_SESSION_CACHE.put(username,session);
        String message = "欢迎用户["+username+"]来到聊天室";
        log.info(message);
        WebSocketUtil.sendMessageAll(message);
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username,String message){
        log.info(message);
        WebSocketUtil.sendMessageAll("用户["+username+"]:"+message);
    }

    @OnClose
    public void onClose(@PathParam("username")String username,Session session){
        // 从当前缓存的Session聊天室中移除
        WebSocketUtil.LIVING_SESSION_CACHE.remove(username);
        WebSocketUtil.sendMessageAll("用户["+username+"]已下线");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    @GetMapping("/chat-room/{sender}/to/{receive}")
    public void onMessage(@PathVariable("sender")String sender,@PathVariable("receive") String receive,String message){
        WebSocketUtil.sendMessage(WebSocketUtil.LIVING_SESSION_CACHE.get(receive),"["+sender+"]"+"->["+receive+"]:"+message);
    }
}
