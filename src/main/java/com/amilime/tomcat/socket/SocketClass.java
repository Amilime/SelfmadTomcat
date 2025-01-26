package com.amilime.tomcat.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClass {
    public static void main(String[] args)throws IOException {
        ServerSocket serverSocket =new ServerSocket(8080);
        System.out.println("======服务启动成功=========");
        // 客户端请求
        while(!serverSocket.isClosed()){
            Socket socket =serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            System.out.println("执行请求:" + Thread.currentThread());
            System.out.println("====收到请求=====");
            BufferedReader read =new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

            String msg =null;

            //http协议
            while((msg = read.readLine())!= null){
                if(msg.length()==0){
                    break;
                }
                System.out.println(msg);
            }

            String resp = "OK";
            OutputStream outputStream = socket.getOutputStream();
            System.out.println(resp);
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
            socket.close();
        }
    }
}
