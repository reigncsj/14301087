package javaee;
import java.net.*;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import mvc.DispatcherServlet;

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
			 // ����Ƿ��ǹرշ�������
			 if(request.getUri()==null){
				 so.close();
			 } 
			 else if (!request.getUri().equals(SHUTDOWN_COMMAND)) {
				// ���� Response ����
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
				  
				 DispatcherServlet d=new DispatcherServlet();
				 d.service(request, response);
				 }
				 
			 }

			 // �ر� socket
			 so.close();
		} catch (Exception e) {
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





	 


