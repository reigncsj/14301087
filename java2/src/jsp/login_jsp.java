package jsp;
import javax.servlet.*;
import java.io.*;

public class login_jsp implements Servlet{
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
out.println("<html>");
out.println(" <head>");
out.println("  <title>登陆</title>");
out.println(" <script type=\"text/javascript\" src=\"js/jquery-3.0.0.min.js\"></script>");
out.println(" </head>");
out.println(" <body>");
out.println("  <form method=\"POST\" name=\"frmLogin\" action=\"LoginServlet\">");
out.println("   <h1 align=\"center\">用户登录</h1>");
out.println("   <div align=\"center\">");
out.println("   <table border=1 >");
out.println("     <tr>");
out.println("      <td>用户账号：</td>");
out.println("      <td>");
out.println("       <input type=\"text\" name=\"username\" value=\"\" size=\"20\" maxlength=\"11\" onkeyup=\"this.value=this.value.replace(/\\D/g,'')\" />");
out.println("      </td>");
out.println("     </tr>");
out.println("     <tr>");
out.println("      <td>密&nbsp;&nbsp;码：</td>");
out.println("      <td>");
out.println("       <input type=\"password\" name=\"password\" value=\"\" size=\"20\" maxlength=\"20\" />");
out.println("      </td>");
out.println("     </tr>");
out.println("     <tr>");
out.println("      <td>");
out.println("       <input type=\"submit\" name=\"Submit\" value=\"提交\" onClick=\"return validateLogin()\" />");
out.println("      </td>");
out.println("      <td>");
out.println("       <input type=\"reset\" name=\"Reset\" value=\"重置\" />");
out.println("      </td>");
out.println("     </tr>");
out.println("    </table>");
out.println("   </div>");
out.println("  </form>");
out.println("  <script language=\"javascript\">");
out.println("   function validateLogin(){");
out.println("    var sUserName = document.frmLogin.userId.value ;");
out.println("    var sPassword = document.frmLogin.password.value ;");
out.println("    if ((sUserName == \"\")){");
out.println("     alert(\"请输入用户名!\");");
out.println("     return false ;");
out.println("    }");
out.println("    else{");
out.println("    	var tt= /^[0-9]*[1-9][0-9]*$/;");
out.println("    	if(!tt.test(document.frmLogin.userId.value)){");
out.println("    		 alert(\"账号输入有误\");");
out.println("    	     return false ;");
out.println("    	}");
out.println("    }");
out.println("    if ((sPassword ==\"\")){");
out.println("     alert(\"请输入密码!\");");
out.println("     return false ;");
out.println("    }");
out.println("   }");
out.println("  </script>");
out.println(" </body>");
out.println("</html>");
out.println("");
   }
}
