package com.amilime.tomcat.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import com.amilime.tomcat.servlet.HttpAmServlet;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 第三版tomcat
 * 第一版单线程处理，简单构建请求和响应处理
 * 第二版分层设计多线程处理，单独罗列请求和响应。并有较好的扩展性并加入了servlet（抽象接口写servlet
 * 第三版进一步增强拓展能力，加入了web.xml使用,servlet的类用web,xml注册进入(dom4j处理挺难的，不大熟悉）
 */
public class StartTomcat {
    private static final int port = 8085;
    //下面两个map给xml解析用的
    public static final HashMap<String, HttpAmServlet> servletMapping = new HashMap<>();
    public static final HashMap<String, String> urlMapping = new HashMap<>();

    /**
     * 1.29更新信息
     * urlmapping映射将返回的uri: / 或是其他 在web.xml里头找寻路径。比如 /user可以在web.xml里找到UserServlet
     * 所以如果只用/ 则需要在uri里添加规则，同时写servlet的方法。
     */

    // 启动命令 入口
    public static void main(String[] args) {
        StartTomcat startTomcat = new StartTomcat();

        startTomcat.start();
    }
    public void start(){
        init();
        // 初始化套接字地址
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("=====启动成功=====");
            int serverNumber = 0;
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                serverNumber = serverNumber+1;
               // RequestHandler requestHandler =new RequestHandler(socket); 第二版
                RequestHandler2 requestHandler =new RequestHandler2(socket);
                System.out.println("第"+serverNumber+"次线程连接的requestHandler是"+requestHandler);
                new Thread(requestHandler).start();
                System.out.println("执行请求:"+Thread.currentThread().getName());
                Thread.sleep(1000);
                ///synchronized (requestHandler){
               ///     requestHandler.wait();
               /// }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } //catch (InterruptedException e) {
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //   throw new RuntimeException(e);
      //  }

        // 读取servlet注册信息

    }
    private void init(){
        //dom4j 这里在处理web.xml (一大块
        try{
            //读到classpath
            String path = StartTomcat.class.getResource("/").getPath();
            //读取器
            SAXReader reader = new SAXReader();
            //xml文档模型
            Document document = reader.read(path+"web.xml");
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element element:elements){
                if("servlet".equalsIgnoreCase(element.getName())){
                    Element servletName = element.element("servlet-name");
                    Element servletClass = element.element("servlet-class");
                    System.out.println(servletName.getText()+"->"+servletClass.getText());
                    // 维护到对应的name和class 类似哈希map
                    //这里要通过反射来拿，然后强转 最后拿到名称和对应的实例
                    servletMapping.put(servletName.getText(),(HttpAmServlet) Class.forName(servletClass.getText().trim()).getDeclaredConstructor().newInstance());
                }else if("servlet-mapping".equalsIgnoreCase(element.getName())){ // 映射
                    Element servletName = element.element("servlet-name");
                    Element urlpattern = element.element("url-pattern");
                    System.out.println(servletName.getText()+"->"+urlpattern.getText());
                    urlMapping.put(urlpattern.getText(),servletName.getText());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
