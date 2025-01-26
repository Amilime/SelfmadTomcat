package com.amilime.tomcat.socket;

import com.amilime.tomcat.http.Request;
import com.amilime.tomcat.http.Response;
import com.amilime.tomcat.servlet.HttpAmServlet;
import com.amilime.tomcat.socket.StartTomcat;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
/**
 * 其实是第三版本，第一个是都在一个socketclass里面
 * socketclass是第一个版本tomcat
 * Mytomcat是第二个版本的tomcat
 * StartTomcat是第三个版本的Tomcat
 */
public class RequestHandler2 implements Runnable{
    public Socket socket;
    public RequestHandler2(Socket socket){
        this.socket = socket;

        System.out.println("servletMapping: " + StartTomcat.servletMapping);
        System.out.println("urlMapping: " + StartTomcat.urlMapping);
    }


// 找到请求
    @Override
    public void run() {
        InputStream inputStream = null;
        try{
            System.out.println(socket.getInputStream());
            Request request = new Request(socket.getInputStream());
            Response response = new Response(socket.getOutputStream());

            String uri = request.getUri();
            System.out.println("请求的uri是:"+uri);
            //StartTomcat.urlMapping.get(uri);
            String servletNam = StartTomcat.urlMapping.get(uri);
            System.out.println("获取到的servletNam"+servletNam);

            //StartTomcat.servletMapping.get(servletNam);
            HttpAmServlet handler = StartTomcat.servletMapping.get(servletNam);
            if(handler != null){
                handler.service(request,response);
            }else {
                //找不到对应的handler处理类
                String responsee = Response.responseHeader + "404!";
                OutputStream outputStream = socket.getOutputStream();
                System.out.println(responsee);
                outputStream.write(responsee.getBytes());
                outputStream.flush();
                outputStream.close();
                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
