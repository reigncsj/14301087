package jsp;
import javax.servlet.*;
import java.io.*;

public class index_jsp implements Servlet{
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
out.println("<body>");
out.println("<form action=\"hello\" method=\"post\">");
out.println("<table>");
out.println("<tr><td>用户名</td><td><input type=\"text\" name=\"name\"></td></tr>");
out.println("<tr><td>密码</td><td><input type=\"password\" name=\"pas\"></td></tr>");
out.println("<tr><td><input type=\"submit\" value=\"提交\"></td><td><input type=\"reset\" value=\"取消\"></td></tr>");
out.println("</table>");
out.println("</form>");
out.println("</body>");
out.println("</html>");
out.println("");
   }
}
