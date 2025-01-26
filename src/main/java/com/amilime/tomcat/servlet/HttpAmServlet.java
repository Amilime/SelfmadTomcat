package com.amilime.tomcat.servlet;

import com.amilime.tomcat.http.Request;
import com.amilime.tomcat.http.Response;

/**
 * 读servlet的源码的时候感觉现在通常都是使用分层设计思想来提升可扩展性，还挺好玩的
 */
public abstract class HttpAmServlet implements AmServlet {
    // service根据请求类型不同调用不同方法

    @Override
    public void service(Request request, Response response)throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            this.doGet(request,response);
        }else if("POST".equalsIgnoreCase(request.getMethod())){
            this.doPost(request,response);
        }
    }

    public abstract void doPost(Request request, Response response) ;

    public abstract void doGet(Request request, Response response) ;
}
