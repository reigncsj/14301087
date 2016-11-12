package Servlet;
import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
public class LoginServlet implements Servlet{
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
	request.setCharacterEncoding("utf-8");
    String username = request.getParameter("username");
    String password = request.getParameter("paw");
    System.out.println(username+password);
   }
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
