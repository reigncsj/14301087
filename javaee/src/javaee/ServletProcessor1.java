package javaee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServletProcessor1 {
	public ServletProcessor1(){}
	public void process(Request request, Response response) {

		 String uri = request.getUri();
		 String servletName = uri.substring(uri.lastIndexOf("/"));
		 String road=getServlet(servletName);
		 
		 //������������ڴ�ָ��JAR�ļ���Ŀ¼������
		 URLClassLoader loader = null;
		 try{
				
			            URL[] urls = new URL[1];
			 			URLStreamHandler streamHandler = null;
			 			File classPath = new File(Constants.WEB_SERVLET_ROOT);
			 			String repository = new URL("file",null,classPath.getCanonicalPath()+ File.separator).toString();
			 			urls[0] = new URL(null,repository,streamHandler);
			 			loader = new URLClassLoader(urls);
			 		}catch(IOException e){
			 			System.out.println(e.toString());
			 		}
			 		
			 		Class myClass = null;
			 		try{
			 			myClass = loader.loadClass(road);
			 		}catch(ClassNotFoundException e){
			 			System.out.println("�޷��ҵ���servlet.class�ļ�");
			 		}
			 		
			 		Servlet servlet = null;
			 		try{
			 			servlet = (Servlet) myClass.newInstance();
			 			servlet.service((ServletRequest)request, (ServletResponse)response);
			 		}catch(Exception e){
			 			System.out.println("�����ڴ�servlet");
			 		}catch(Throwable e){
			 			System.out.println(e.toString());
			 		}
	}
	private String getServlet(String serName) {
		SAXReader reader = new SAXReader();  
		String name=null;
		String road="";
	     try {
			Document  document = reader.read(new File("web.xml"));
			Element root=document.getRootElement();
			List root1= root.elements("servlet-mapping");
			for (Iterator it = root1.iterator(); it.hasNext();) {      
				Element elm = (Element) it.next();  
				Element el=elm.element("url-pattern");
				String pppp=el.getText();
				if(el.getText().equals(serName)){
					name=elm.element("servlet-name").getText();
					break;
				}
			}
			if(name!=null){
				List root2= root.elements("servlet");
				for (Iterator it = root2.iterator(); it.hasNext();) {      
					Element elm = (Element) it.next();  
					Element el=elm.element("servlet-name");
					if(el.getText().equals(name)){
						road=elm.element("servlet-class").getText();
						break;
					}
				}
				return road;
			}

		} catch (DocumentException e) {
			return road;
		} 
         return road;
	}
}
