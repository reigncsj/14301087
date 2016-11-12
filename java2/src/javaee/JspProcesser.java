package javaee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class JspProcesser {
	public void process(Request request, Response response) {
		String jspName = request.getUri();
		String jsp = jspName.substring(jspName.lastIndexOf("/"));
		String jsp2="jsp."+jsp.replace(".","_").substring(1);
		String jsp1=jsp.replace(".","_")+".class";
		jsp=jsp.replace(".","_")+".java";
		File file=new File(Constants.WEB_ROOT+jspName);
		if(!file.exists()){
			sendError(response.getOutPutStream());
		}else{
			buildJavaFile(file,jsp);
			URLClassLoader loader = null;
			
				JavaCompiler jc=ToolProvider.getSystemJavaCompiler();
				StandardJavaFileManager sj=jc.getStandardFileManager(null, null, null);
				Iterable it=sj.getJavaFileObjects(Constants.WEB_JSP_SRC_ROOT+jsp);
				CompilationTask task=jc.getTask(null, sj, null, null, null, it);
				task.call();
				try {
					sj.close();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			
			try {
				   URL[] urls = new URL[1];
		 			URLStreamHandler streamHandler = null;
		 			File classPath = new File(Constants.WEB_JSP_Servlet_ROOT);
		 			String repository = new URL("file",null,classPath.getCanonicalPath()+ File.separator).toString();
		 			urls[0] = new URL(null,repository,streamHandler);
		 			loader = new URLClassLoader(urls);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Class myClass = null;
	 		try{
	 			myClass = loader.loadClass(jsp2);
	 		}catch(ClassNotFoundException e){
	 			System.out.println("无法找到servlet");
	 		}
	 		
	 		Servlet servlet = null;
	 		try{
	 			servlet = (Servlet) myClass.newInstance();
	 			servlet.service((ServletRequest)request, (ServletResponse)response);
	 		}catch(Exception e){
	 			System.out.println("无法使用servlet");
	 		}catch(Throwable e){
	 			System.out.println(e.toString());
	 		}
		}
		
	}
	
	
	private void buildJavaFile(File f,String jsp){
		
		BufferedReader fis =null;
		try {
			File f1=new File(Constants.WEB_JSP_SRC_ROOT+"\\"+jsp);
			if(f1.exists())
				f1.delete();
			f1.createNewFile();
			fis= new BufferedReader(new FileReader(f));
			FileWriter fos=new FileWriter(f1);
			buildContent(fis,fos,jsp);
		    
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void buildContent(BufferedReader br,FileWriter fw,String jsp) throws IOException{
		 String sb1="";
		 String str="";
		 while((str=br.readLine())!=null){
			 sb1+=str+"\r\n";
		 }
	     char[] buf=sb1.toCharArray();   
	        
		fw.write("package jsp;\r\n");
		fw.write("import javax.servlet.*;\r\nimport java.io.*;\r\n");
		int i=0;
		StringBuffer sb=new StringBuffer();
		for(;i<buf.length;i++){
			if(buf[i]=='<'&&buf[i+1]=='%'&buf[i+2]=='@'){
			     for(;i<buf.length;i++){
			    	 sb.append((char)buf[i]);
			    	 if(buf[i]=='%'&&buf[i+1]=='>'){
			    		 sb.append((char)buf[++i]);
			    		 setHeader(sb.toString(),fw,jsp);
			    		 sb=new StringBuffer();
			    		 break;
			    	 }
			     }
			}
			else if(buf[i]=='<'&&buf[i+1]=='%'&&buf[i+2]=='='){
				fw.write("out.println(\""+sb.toString()+"\");");
				sb=new StringBuffer();
				i=i+3;
				for(;i<buf.length;i++){
					if(buf[i]=='%'&&buf[i+1]=='>'){
						fw.write("out.print("+sb.toString()+");");
						 sb=new StringBuffer();
						 i++;
			    		 break;
					}
					else
						sb.append((char)buf[i]);
				}
			}
			else if(buf[i]=='<'&&buf[i+1]=='%'){
				fw.write("out.println(\""+sb.toString()+"\");");
				sb=new StringBuffer();
				i=i+2;
				for(;i<buf.length;i++){
					if(buf[i]=='%'&&buf[i+1]=='>'){
						fw.write(sb.toString());
						 sb=new StringBuffer();
						 i++;
			    		 break;
					}
					else
						sb.append((char)buf[i]);
				}
			}
			else if(buf[i]=='\r'&&buf[i+1]=='\n'){
				fw.write("out.println(\""+sb.toString()+"\");\r\n");
				sb=new StringBuffer();
				i++;
		    }
			else{
				if(buf[i]=='"'||buf[i]=='\\'){
					sb.append('\\');
				    sb.append((char)buf[i]);
				}
				else
					 sb.append((char)buf[i]);
			}
		}
		fw.write("out.println(\""+sb.toString()+"\");\r\n");
		
		fw.write("   }\r\n");
		fw.write("}\r\n");
		fw.flush();
		fw.close();
		
		
	}
	
	private void setHeader(String s,FileWriter fw,String jsp) throws IOException{
		s=s.substring(3,s.length()-2);
		String[] att=s.split(" ");
		String charset="utf-8";
		String[] imp=null;
		for(int i=0;i<att.length;i++){
			
			if(att[i].indexOf("import=")!=-1){
				int p=att[i].indexOf("import=");
				imp=att[i].substring(p+8,att[i].length()-1).split(",");
				}
			}
		
	   if(imp!=null){
		   for(int k=0;k<imp.length;k++){
			   fw.write("import "+imp[k]+";\r\n");
		   }
	   }
	   fw.write("\r\npublic class "+jsp.substring(1,jsp.indexOf("."))+" implements Servlet{\r\n");
	   fw.write("@Override\r\npublic void destroy() {}\r\n\r\n");
	   fw.write("@Override\r\npublic ServletConfig getServletConfig() {return null;}\r\n\r\n");
       fw.write("@Override\r\npublic String getServletInfo(){return null;}\r\n\r\n");
       fw.write("@Override\r\npublic void init(ServletConfig arg0) throws ServletException{}\r\n\r\n");
       fw.write("public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {\r\n");
	   //fw.write("       request.setCharacterEncoding(\""+charset+"\");\r\n");
	   fw.write("   PrintWriter out = response.getWriter();\r\n");
	}
	
	
	
	private void sendError(OutputStream out) {
		OutputStream output=out;
		String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
				+ "Content-Type: text/html\r\n"
				+ "\r\n" + "<h1>File Not Found</h1>";
			try {
				output.write(errorMessage.getBytes());
				output.flush();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			
	}

}
