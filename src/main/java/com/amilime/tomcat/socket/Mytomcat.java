package com.amilime.tomcat.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 理解tomcat内容
 * 第一步、理解socket传输 http协议内容
 * 第二步、解析http协议交给servlet处理
 */
public class Mytomcat {
    public static void main(String[] args)throws IOException {
        int port;
        port =8080;
        ServerSocket serverSocket =new ServerSocket(port);
        System.out.println("======服务启动成功,监听端口"+port);
        // 客户端请求
        while(!serverSocket.isClosed()){
            Socket socket =serverSocket.accept();
            // 解析Request
            RequestHandler requestHandler = new RequestHandler(socket);
            new Thread(requestHandler).start();
        }
    }
}
