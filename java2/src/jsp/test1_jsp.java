package jsp;
import javax.servlet.*;
import java.io.*;

public class test1_jsp implements Servlet{
@Override
public void destroy() {}

@Override
public ServletConfig getServletConfig() {return null;}

@Override
public String getServletInfo(){return null;}

@Override
public void init(ServletConfig arg0) throws ServletException{}

public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
   PrintWriter out = response.getWriter();
out.println("");
out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
out.println("<html>");
out.println("<head>");
out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
out.println("<title>Insert title here</title>");
out.println("</head>");
out.println("");int i=5; out.println("");
out.println("<body>");
out.println("值=");out.print(i );out.println("");
out.println("</body>");
out.println("</html>");
out.println("");
   }
}
