package javaee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

public class Response implements ServletResponse {

	private static final int BUFFER_SIZE = 1024;
	private Request request;
	private OutputStream output;
	
	public Response(OutputStream output) {
		
		this.output = output;
		
	}
	
	public void setRequest(Request request) {
		
		this.request = request;
		
	}
	
	public OutputStream getOutPutStream(){
		return output;
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
				output.flush();
				output.close();

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
		return (ServletOutputStream) output;
	}

	public void setContentLengthLong(long arg0) {
		// TODO Auto-generated method stub
		
	}

}
