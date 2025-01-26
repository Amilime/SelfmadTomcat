package com.amilime.tomcat.socket;

import com.amilime.tomcat.http.Response;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

        public Socket socket;
    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try{
            inputStream =socket.getInputStream();
            System.out.println("执行请求:"+Thread.currentThread().getName());
            System.out.println("收到请求");
            BufferedReader read =new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

            String msg = null;

            //http 协议
            while((msg = read.readLine())!= null){
                if(msg.length()==0){
                    break;
                }
                System.out.println(msg);
            }
            // http响应
            String resp = Response.responseHeader+ "OK we did it!"; // 这里封装了响应前三个部分
            OutputStream outputStream = socket.getOutputStream();
            System.out.println(resp);
            outputStream.write(resp.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (socket != null){
                try{
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
