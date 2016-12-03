package mvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javaee.Constants;
import javaee.Response;

public class Render {
	public static void render(ModelAndView mav,Response response){
		String sb1="";
		String str="";
		String name=mav.getName();
		File file=new File(Constants.WEB_ROOT+File.separator+name+".jsp");
		if(file.exists()){
			try {
				BufferedReader br= new BufferedReader(new FileReader(file));
				PrintWriter pw=response.getWriter();
				Pattern p=Pattern.compile("\\$\\{.*?\\}");
				Pattern p1=Pattern.compile("<%@([\\S\\s]*?)%>");
				 while((str=br.readLine())!=null){
					 Matcher m = p.matcher(str);
				        ArrayList<String> strs = new ArrayList<String>();
				        while (m.find()) {
				            strs.add(m.group(0));            
				        } 
				        for (String s : strs){
				        	String s1=s.substring(2,s.length()-1);
				        	String s2=(String) mav.getMap(s1);
				           if(s2!=null){
				        	   str=str.replace(s,s2);
				           }
				        }       
					 sb1+=str+"\r\n";
				 }
				 Matcher m = p1.matcher(sb1);
				 while (m.find()) {
			           sb1=sb1.replace(m.group(), "") ;           
			        } 
				 pw.write(sb1);
				 pw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			sendError(response.getOutPutStream());
		}
	}
	private static void sendError(OutputStream out) {
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
