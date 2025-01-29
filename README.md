这里逐渐递进的做了3个版本的自制tomcat容器

## 版本1
仅存放于SocketClass.java中
演示了网页端和服务器的socket连接和字节流传输，还有http协议内容(req和resp)

## 版本2
使用了分层的设计
在http中定义好了Req和Resp的内容。其中Req的http协议解析是为了符合servlet规范传输内容
同时，定义了RequestHandler专门用于处理协议的类，并在StartTomcat.java中转为多线程处理请求。

## 版本3
定义了web.xml 并用dom4j处理相关的内容，将其用哈希转化绑定后丢给RequestHandler2处理
hander2则通过servlet的HttpAmServlet进行分析处理，解析协议内容（get/post 状态码）并据此调用
对应的方法(接口AmServlet也是提高可扩展性的一个方式)从而自己定义第三方UserServlet的响应内容，也就是写处理逻辑。
web.xml里的/user等路由则直接连接到自定义的第三方处理逻辑中，从而得到需要的response。同时在web.xml里定义
路由和逻辑显然比前者都轻松很多。

## 不足
返回状态码的地方有一些小bug，还没修。

请求发送只有get 和post 且没有写完善的处理逻辑。

线程处理不行，只是用了Bio，在nio和阻塞的改进上没什么想法……

资源释放和异常处理和日志都不够好。

截断方式不大熟悉 dom4j不熟

生命周期没考虑

总体而言只能是作为一个理解servlet和tomcat的项目。相当多的逻辑控制并没有写出。
但是就对javaweb理解上而言，确实有一种醍醐灌顶的感觉。

## 1/29号更新
最初版本存在路由问题 即从web.xml找到userservlet的问题  修改后允许uri使用/ /user /favicon.ico
网页端一次会发送两个请求，一个是localhost一个是favicon.ico  
所以都需要对其进行解析并返回响应。


