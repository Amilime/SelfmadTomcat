package com.amilime.tomcat.servlet;

import com.amilime.tomcat.http.Request;
import com.amilime.tomcat.http.Response;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 这里主要写处理逻辑
 * 响应逻辑多线程放到其他地方
 */
public class NonServlet extends HttpAmServlet{

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destory() {

    }

    @Override
    public void doPost(Request request, Response response) {
        OutputStream outputStream = response.outputStream;
        String result = Response.responseHeader+ "connect succeed port:8085  I am tomcat1 with uri:/";
        try {
            outputStream.write(result.getBytes());
            outputStream.flush();
            outputStream.close();;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doGet(Request request, Response response) {
        this.doPost(request,response);
    }
}
