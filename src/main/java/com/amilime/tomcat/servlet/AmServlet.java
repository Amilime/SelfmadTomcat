package com.amilime.tomcat.servlet;

import com.amilime.tomcat.http.Request;
import com.amilime.tomcat.http.Response;

/**
 * 自己构造个servlet
 * servlet规范:
 * 初始化
 * 传入请求，把请求处理好后在response中返回出去
 * 销毁
 */
public interface AmServlet {
    void init() throws Exception;

    void service(Request request, Response response) throws Exception;

    void destory();
}
