package com.amilime.tomcat.http;

import java.io.OutputStream;

/**
 * 输出流
 * 响应类，满足状态行 + 响应报文头部 +空行 +响应正文
 */
public class Response {
    public OutputStream outputStream;

    public static final String responseHeader ="HTTP/1.1 200 \r\n"+
                "Content-Type:text/html\r\n" +"\r\n"; // 两个\r\n是流留出一个空行

    public Response(OutputStream outputStream){
        this.outputStream=outputStream;
    }
}
