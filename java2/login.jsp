	<%@ page language="java" contentType="text/html;charset=utf-8"%>
<html>
 <head>
  <title>登陆</title>
 <script type="text/javascript" src="js/jquery-3.0.0.min.js"></script>
 </head>
 <body>
  <form method="POST" name="frmLogin" action="LoginServlet">
   <h1 align="center">用户登录</h1>
   <div align="center">
   <table border=1 >
     <tr>
      <td>用户账号：</td>
      <td>
       <input type="text" name="username" value="" size="20" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')" />
      </td>
     </tr>
     <tr>
      <td>密&nbsp;&nbsp;码：</td>
      <td>
       <input type="password" name="password" value="" size="20" maxlength="20" />
      </td>
     </tr>
     <tr>
      <td>
       <input type="submit" name="Submit" value="提交" onClick="return validateLogin()" />
      </td>
      <td>
       <input type="reset" name="Reset" value="重置" />
      </td>
     </tr>
    </table>
   </div>
  </form>
  <script language="javascript">
   function validateLogin(){
    var sUserName = document.frmLogin.userId.value ;
    var sPassword = document.frmLogin.password.value ;
    if ((sUserName == "")){
     alert("请输入用户名!");
     return false ;
    }
    else{
    	var tt= /^[0-9]*[1-9][0-9]*$/;
    	if(!tt.test(document.frmLogin.userId.value)){
    		 alert("账号输入有误");
    	     return false ;
    	}
    }
    if ((sPassword =="")){
     alert("请输入密码!");
     return false ;
    }
   }
  </script>
 </body>
</html>