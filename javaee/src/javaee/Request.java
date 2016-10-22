package javaee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class Request implements ServletRequest{
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
	          for(int j=0;j<i;j++){
	        	  request.append((char)buffer[j]);
	          }
	          String il="";
	          try {
				
	        	  il=URLDecoder.decode(request.toString(), "utf-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	          
	            
	          System.out.println("----------------");  
	          System.out.print(il);  
	          System.out.println("----------------");  
	      
	          uri = parseUri(il);  
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

	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getContentLengthLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

