package jsp;
import javax.servlet.*;
import java.io.*;
import com.beans.*;
import com.service.GetTableService;
import com.dao.impl.*;

public class getTable_jsp.java implements Servlet{
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
out.println("
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap-combined.min.css" rel="stylesheet"
	media="screen">
<!--  link href="css/bootstrapSwitch.css" rel="stylesheet">-->

<!-- Custom styles for this template -->
<script type="text/javascript" src="js/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/water.js"></script>
<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/data.js"></script>
</head>
<body>
<div class="Container-fluid">
<a href="#scroll" style="position: fixed">£¤??£¤????????</a>
	<div class="row-fluid">
		<div id="setChart" >
		</div>
		<div class="table table-striped table-responsive span2" id="chartDiv" style="height: px">
		</div>
	</div>
	");
		String id = request.getParameter("id");
		String tname = request.getParameter("name");
		
		int tpage=Integer.parseInt(request.getParameter("page"));
		int all=Integer.parseInt(request.getParameter("all"));
		GetTableService gts = new GetTableService();
		SelectBean sb;
		if(all==0){
			all= gts.getAllPage(id, tname);
			SelectDao sd=new SelectDao();
			sb=sd.getSelect(Integer.parseInt(id), tname);
			session.setAttribute("select",sb);
		}
		else{
			sb=(SelectBean)session.getAttribute("select");
		}
		Data data = gts.getData(id, tname,tpage);
		if (data.getNum() != 0) {
	out.println("
	");out.print(tname);out.println("??????
	<br>
	<div id="scroll" style="position:fixed; top:350px"></div>
	<div id="dataDiv" class="table table-striped table-responsive" >
		<table id="chartTable" class="table" >
			<thead>
			<tr>
				");
					for (int i = 0,j=0; i < data.getNum() - 1; i++) {
						if(sb.getSec()[i].equals("1")){
				out.println("
				<th name="");out.print( j+1 );out.println("" class="chartRow" >");out.print(data.getOneName(i));out.println("</th>
				");
						j++;}}
				out.println("
			</tr>
			</thead>
			<tbody>
			");
				for (int c = 0; c < data.getCol(); c++) {
			out.println("
			<tr>
				");
					for (int i = 0; i < data.getNum()-1; i++) {
						if(sb.getSec()[i].equals("1")){
				out.println("
				<td>");out.print(data.getOneData(c, i));out.println("</td>
				");
					}}
				out.println("
			</tr>
			");
				}
				}
			out.println("
			</tbody>
		</table>
		</div>
		<table>
		<tr>
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=1&all=");out.print(all );out.println("" target="center">??????</a></td>
			");if(tpage==1){ out.println("
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=1&all=");out.print(all );out.println("" target="center">©W??©W?????</a></td>
			");}else{ out.println("
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=");out.print(tpage-1 );out.println("&all=");out.print(all );out.println("" target="center">©W??©W?????</a></td>
			");} out.println("
			");if(tpage==all){ out.println("
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=");out.print(all );out.println("&all=");out.print(all );out.println("" target="center">©W??©W?????</a></td>
			");}else{ out.println("
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=");out.print(tpage+1 );out.println("&all=");out.print(all );out.println("" target="center">©W??©W?????</a></td>
			");} out.println("
			<td><a href="getTable.jsp?id=");out.print(id);out.println("&name=");out.print(tname );out.println("&page=");out.print(all );out.println("&all=");out.print(all );   }
}
