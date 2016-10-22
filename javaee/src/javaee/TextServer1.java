package javaee;
import java.net.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

public class TextServer1 extends Thread {
	 private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	Socket so=null;
	public TextServer1(Socket s){
		so=s;
	}
	
	public void run() {
		try {
			
			 Request request = new Request(so.getInputStream());
			 request.parse();
			 // 检查是否是关闭服务命令
			 if(request.getUri()==null){
				 so.close();
			 } 
			 else if (!request.getUri().equals(SHUTDOWN_COMMAND)) {
				// 创建 Response 对象
				 Response response = new Response(so.getOutputStream());
				 response.setRequest(request);

				 if (request.getUri().indexOf(".html")!=-1||request.getUri().indexOf(".ico")!=-1||(
						 request.getUri().indexOf(".js")!=-1&&request.getUri().indexOf(".jsp")==-1)) {
					 StaticResourceProcessor processor = new StaticResourceProcessor();
					  processor.process(request, response);
				  
				 }else if (request.getUri().indexOf(".jsp")!=-1) {
					 JspProcesser processor=new JspProcesser();
					 processor.process(request, response);
				 } 
				 else {
				  //servlet
					 ServletProcessor1 processor = new ServletProcessor1();
					  processor.process(request, response);
				 }
			 }

			 // 关闭 socket
			 so.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		  ServerSocket sso =new ServerSocket(8889);
		  while(true){
			  Socket soo=sso.accept();
			  TextServer1 ts=new TextServer1(soo);
			  ts.start();
		  }
	}
}





	 


