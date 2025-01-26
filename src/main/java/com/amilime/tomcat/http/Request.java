package com.amilime.tomcat.http;

import java.io.*;

/**
 * http解析协议
 * 下一步给servlet (servlet规范 可以去看接口）
 */
public class Request {

    // 请求资源路径
    private String uri;

    // 请求类型
    private String method;

    public Request(InputStream inputStream)throws UnsupportedEncodingException{
        // 解析字节码流中的请求信息
        try{

            BufferedReader read =new BufferedReader(new InputStreamReader(inputStream,"utf-8"));

        //    while ((line = read.readLine()) !=null){
        //        System.out.println("字节码流中的请求信息是"+line);
        //    }

         //   String[] data = read.readLine().split(" "); // 用空格分割，第一个是方法第二个是uri

            String line;
            while ((line = read.readLine()) != null){
                System.out.println("字节码流中的请求信息是" + line);

                if (line.startsWith("GET") || line.startsWith("POST")){
                    //分割获取method uri
                    String[] data = line.split(" ");
                    this.uri = data[1];
                    this.method = data[0];
                    System.out.println("获取到的uri是"+data[1]);
                    System.out.println("获取到的方法是"+data[0]);
                    break;
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}