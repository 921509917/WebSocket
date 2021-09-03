package com.duan.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName SocketSpringApplication
 * @Author DuanJinFei
 * @Date 2021/4/25 14:46
 * @Version 1.0
 */
@EnableWebSocket
@SpringBootApplication
public class SocketSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketSpringApplication.class);
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
