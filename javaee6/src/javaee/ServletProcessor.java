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

public class ServletProcessor {
	public ServletProcessor(){}
	public void process(Request request, Response response) {
		 String road=getServlet();
		 
		 //类加载器，用于从指定JAR文件或目录加载类
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
			 			System.out.println("无法找到此servlet.class文件");
			 		}
			 		
			 		Servlet servlet = null;
			 		try{
			 			servlet = (Servlet) myClass.newInstance();
			 			servlet.service((ServletRequest)request, (ServletResponse)response);
			 		}catch(Exception e){
			 			System.out.println("不存在此servlet");
			 		}catch(Throwable e){
			 			System.out.println(e.toString());
			 		}
	}
	
	private String getServlet() {
		SAXReader reader = new SAXReader();  
		String name=null;
		String road="";
	     try {
			Document  document = reader.read(new File("web.xml"));
			Element root=document.getRootElement();
			List root1= root.elements("servlet");
			for (Iterator it = root1.iterator(); it.hasNext();) {      
				Element elm = (Element) it.next();  
				Element el=elm.element("servlet-name");
				String class1=el.getText();
				if(class1.equals("DispatcherServlet")){
					Element e2=elm.element("servlet-class");
					road=e2.getText();
					break;
				}
			}
			
				return road;
			

		} catch (DocumentException e) {
			return road;
		} 

	}
}
