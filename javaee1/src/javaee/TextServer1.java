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

				 if (request.getUri().indexOf(".html")==-1) {
				  //请求uri以/servlet/开头，表示servlet请求
				  ServletProcessor1 processor = new ServletProcessor1();
				  processor.process(request, response);
				 } else {
				  //静态资源请求
				  StaticResourceProcessor processor = new StaticResourceProcessor();
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
		  ServerSocket sso =new ServerSocket(8888);
		  while(true){
			  Socket soo=sso.accept();
			  TextServer1 ts=new TextServer1(soo);
			  ts.start();
		  }
	}
}

class Request implements ServletRequest{
	  private InputStream input;  
	  private String uri;
	  private ArrayList<Object> al1=null,al2=null;
	    
	  public Request(InputStream input) {  
	          
	          this.input = input;  
	          al1=new ArrayList<Object>();
	          al2=new ArrayList<Object>();
	      }  
	        
	      public String getUri() {  
	            
	          return uri;  
	            
	      }
	      private void setAttribute(String[] att){
	    	  for(int i=0;i<att.length;i++){
	    		  al1.add(att[i].substring(0, att[i].indexOf("=")));
	    		  String inf=att[i].substring(att[i].indexOf("=")+1);
	    		  
	    		  al2.add(inf);
	    	  }
	      }
	     
	        
	      private String parseUri(String requestString) {  
	          String[] requrl=requestString.split("\\r\\n");
	          if(requrl[0].startsWith("GET")){
	               String[] req1=requrl[0].split(" ");
	               if(req1[1].indexOf("?")!=-1){
	            	   String index=req1[1].substring(req1[1].indexOf("?")+1);
	            	   String[] att=index.split("&");
	            	   setAttribute(att);  
	            	   return req1[1].substring(0, req1[1].indexOf("?"));
	               }
	               return req1[1];
	          }
	          else if(requrl[0].startsWith("POST")){
	        	  String[] req1=requrl[0].split(" ");
	        	  String index=requrl[requrl.length-1];
           	      String[] att=index.split("&");
           	      setAttribute(att);
           	      return req1[1];
	          }
	          else{
	        	  return null;
	          }
	            
	            
	      }  
	        
	      public void parse() {  
	            
	          // Read a set of characters from the socket  
	          StringBuffer request = new StringBuffer(2048);  
	          StringBuffer request1 = new StringBuffer(10);
	          int i;   
	          byte[] buffer = new byte[2048]; 
	          char[] buffer1=new char[10];
	          for(int o=0;o<10;o++)
	               buffer1[o]=' ';
	          try {  
	                
	              i = input.read(buffer);  
	                
	          } catch (IOException e) {  
	                
	              e.printStackTrace();  
	              i = -1;  
	                
	          }  
	          int q=0;
        	  int p=0;
	          for (int j = 0; j < i; j ++) { 
	        	  
	        	  if(p!=0){
	                 if((char) buffer[j]=='%'&&q!=2){
	                	 for(int m=0;m<(p-1)*3+q;m++)
	                		 request.append(buffer1[m]);
	                	 request.append((char) buffer[j]);
	                	 p=0;
	                	 q=0;
	                 }
	                 else if((char) buffer[j]=='%'&&q==2){
	                	 p++;
	                	 q=0;
	                	 buffer1[(p-1)*3+q]='%';
	                 }
	                 else if((char) buffer[j]!='%'&&((q<2&&p!=3)||(q<1&&p==3))){
	                	 q++;
	                	 buffer1[(p-1)*3+q]=(char) buffer[j];
	                 }
	                 else if((char) buffer[j]!='%'&&q==1&&p==3){
	                	 q++;
	                	 buffer1[(p-1)*3+q]=(char) buffer[j];
	                	 String ch="";
	                	 
	                	 for(int l=0;l<9;l++)
	                		 request1.append(buffer1[l]);
	                	 try {
	                		 ch=request1.toString();
							ch=URLDecoder.decode(ch, "utf-8");
							request1.delete(0, 9);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
	                	 request.append(ch);
	                	 p=0;
	                	 q=0;
	                 }
	                 else if((char) buffer[j]!='%'&&q>=2){
	                	 for(int m=0;m<(p-1)*3+q;m++)
	                		 request.append(buffer1[m]);
	                	 request.append((char) buffer[j]);
	                	 p=0;
	                	 q=0;
	                 }
	        	  }
	              else{
	            	  if((char) buffer[j]=='%'){
	            		  buffer1[0]='%';
	            		  p=1;
	            	  }
	            	  else
	                      request.append((char) buffer[j]);  
	              }
	          }  
	            
	          System.out.println("----------------");  
	          System.out.print(request.toString());  
	          System.out.println("----------------");  
	      
	          uri = parseUri(request.toString());  
	      }  
 

	@Override
	public Object getAttribute(String arg0) {
		
		return null;
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		for(int i=0;i<al1.size();i++){
			if(((String)al1.get(i)).equals(arg0))
				return (String) al2.get(i);
		}
		return null;
	}

	@Override
	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttribute(String arg0, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getContentLengthLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

class Constants {
	 public static final String WEB_ROOT = System.getProperty("user.dir")
	 ;
	 public static final String WEB_SERVLET_ROOT = System.getProperty("user.dir")
	 + File.separator + "bin" ;
}

class Response implements ServletResponse {

	private static final int BUFFER_SIZE = 1024;
	private Request request;
	private OutputStream output;
	private PrintWriter writer;
	
	public Response(OutputStream output) {
		
		this.output = output;
		
	}
	
	public void setRequest(Request request) {
		
		this.request = request;
		
	}
	
	/**
	 * This method is used to save a static page
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException {
		
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		
		try {
			
			File file = new File(Constants.WEB_ROOT, request.getUri());
			fis = new FileInputStream(file);
			
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			
			while(ch != -1) {
				
				output.write(bytes, 0, BUFFER_SIZE);
				ch = fis.read(bytes, 0, BUFFER_SIZE);
				
			}
			
		} catch (FileNotFoundException e) {
			
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
				+ "Content-Type: text/html\r\n"
				+ "\r\n" + "<h1>File Not Found</h1>";
			output.write(errorMessage.getBytes());
			
		} finally {
			
			if (fis != null) {
				
				fis.close();
				this.flushBuffer();
				this.getOutputStream().close();
				this.getWriter().close();
			}
			
		}
		
	}
	
	@Override
	public void flushBuffer() throws IOException {
				
	}

	@Override
	public int getBufferSize() {
		
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		
		return null;
	}

	@Override
	public String getContentType() {
		
		return null;
	}

	@Override
	public Locale getLocale() {
		
		return null;
	}


	@Override
	public PrintWriter getWriter() throws IOException {
		
		return new PrintWriter(this.output, true);
	}

	@Override
	public boolean isCommitted() {
		
		return false;
	}

	@Override
	public void reset() {
		
		
	}

	@Override
	public void resetBuffer() {
		
		
	}

	@Override
	public void setBufferSize(int size) {
		
		
	}

	@Override
	public void setCharacterEncoding(String charset) {
		
		
	}

	@Override
	public void setContentLength(int len) {
		
		
	}

	@Override
	public void setContentType(String type) {
		
		
	}

	@Override
	public void setLocale(Locale loc) {	
		
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContentLengthLong(long arg0) {
		// TODO Auto-generated method stub
		
	}

}

class StaticResourceProcessor {
	 public void process(Request request, Response response) {
	    try {
	     response.sendStaticResource();
	    } 
	    catch (IOException e) {
	     e.printStackTrace();
	    }
    }
}

class ServletProcessor1 {
	public ServletProcessor1(){}
	public void process(Request request, Response response) {

		 String uri = request.getUri();
		 String servletName = uri.substring(uri.lastIndexOf("/"));
		 String road=getServlet(servletName);
		 
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
	 


