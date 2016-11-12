<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.beans.*,com.service.GetTableService,com.dao.impl.*"%>
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
<a href="#scroll" style="position: fixed">回到总表</a>
	<div class="row-fluid">
		<div id="setChart" >
		</div>
		<div class="table table-striped table-responsive span2" id="chartDiv" style="height: px">
		</div>
	</div>
	<%
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
	%>
	<%=tname%>表：
	<br>
	<div id="scroll" style="position:fixed; top:350px"></div>
	<div id="dataDiv" class="table table-striped table-responsive" >
		<table id="chartTable" class="table" >
			<thead>
			<tr>
				<%
					for (int i = 0,j=0; i < data.getNum() - 1; i++) {
						if(sb.getSec()[i].equals("1")){
				%>
				<th name="<%= j+1 %>" class="chartRow" ><%=data.getOneName(i)%></th>
				<%
						j++;}}
				%>
			</tr>
			</thead>
			<tbody>
			<%
				for (int c = 0; c < data.getCol(); c++) {
			%>
			<tr>
				<%
					for (int i = 0; i < data.getNum()-1; i++) {
						if(sb.getSec()[i].equals("1")){
				%>
				<td><%=data.getOneData(c, i)%></td>
				<%
					}}
				%>
			</tr>
			<%
				}
				}
			%>
			</tbody>
		</table>
		</div>
		<table>
		<tr>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=1&all=<%=all %>" target="center">首页</a></td>
			<%if(tpage==1){ %>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=1&all=<%=all %>" target="center">上一页</a></td>
			<%}else{ %>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=<%=tpage-1 %>&all=<%=all %>" target="center">上一页</a></td>
			<%} %>
			<%if(tpage==all){ %>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=<%=all %>&all=<%=all %>" target="center">下一页</a></td>
			<%}else{ %>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=<%=tpage+1 %>&all=<%=all %>" target="center">下一页</a></td>
			<%} %>
			<td><a href="getTable.jsp?id=<%=id%>&name=<%=tname %>&page=<%=all %>&all=<%=all %>" target="center">尾页</a></td>
			</tr>
			</table>
	
</div>
</body>
</html>