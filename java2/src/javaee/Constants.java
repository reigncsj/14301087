package javaee;

import java.io.File;

public class Constants {
	 public static final String WEB_ROOT = System.getProperty("user.dir")
	 ;
	 public static final String WEB_SERVLET_ROOT = System.getProperty("user.dir")
	 + File.separator + "bin" ;
	 public static final String WEB_JSP_SRC_ROOT = System.getProperty("user.dir")
	 + File.separator + "src" + File.separator + "jsp" ;
	 public static final String WEB_JSP_Servlet_ROOT = System.getProperty("user.dir")
			 + File.separator + "src" ;
}
