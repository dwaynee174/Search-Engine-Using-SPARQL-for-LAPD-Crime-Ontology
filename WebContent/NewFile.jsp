<%@page import="org.apache.jena.base.Sys"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.sparql"
	import="java.util.ArrayList" import="java.util.Iterator"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="bootstrap.css" rel="stylesheet" />
</head>
<body>


	<div class="container">
		<div class="jumbotron">
			<h2>L.A.P.D CRIME AND COLLISION DATA FOR 2015</h2>
			<br />
			<div class="navbar navbar-inverse navbar-static-top">
				<button class="navbar-toggle" data-toggle="collapse"
					data-target=".navHeaderCollapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<div class="collapse navbar-collapse navHeaderCollapse">
					<ul class="nav navbar-nav navbar-left">
						<li class="active"><a href="NewFile.jsp">HOME</a></li>
						<li class="active"><a href="http://www.lapdonline.org/">LAPD
								Official site</a></li>



					</ul>
				</div>
			</div>
		</div>

		<h3>Enter your search string</h3>
		<form action="Searchcrime" method="get">
			<input class="search2" id="txtSearch" type="text" name="serach_bar"size="31" maxlength="255" value=""style="left: 396px; top: 153px; width: 293px; height: 26px;" /> 
			<input class="search1" type="submit" name="submition" value="Search" style="padding"" bottom: 20px; left: 691px; top: 153px; height: 23px" />

		</form>
<br><br><br>
	
<%
		if (request.getAttribute("query") == "query1") {
		
		%>
		<table width="59%" border="1">
		
       <tr>
			<td>Dr_No</td>
			<td>Location</td>
		</tr>
		
		<%
			ArrayList<String> rows = new ArrayList<String>();
		

				if (request.getAttribute("list") != null) {

					rows = (ArrayList) request.getAttribute("list");

				}
				Iterator iterator = rows.iterator();
				while (iterator.hasNext()) {
		%>
		<tr>
			<td><%=iterator.next()%></td>
			<td><%=iterator.next()%></td>
		</tr>


		<%
			} %>
			</table>
			<% 
			}
else if (request.getAttribute("query") == "query2"){
	%>	
	<table width="59%" border="1">
	<tr>
		<td>Area Name</td>
			<td>Description</td>
			<td>Date Occurance</td>
	</tr>
	<%
				ArrayList<String> rows_1 = new ArrayList<String>();
					

					if (request.getAttribute("list") != null) {

						rows_1 = (ArrayList) request.getAttribute("list");

					}
					
					Iterator iterator = rows_1.iterator();
					while (iterator.hasNext()) {
			%>
			<tr>
			<td><%=iterator.next()%></td>
			<td><%=iterator.next()%></td>
			<td><%=iterator.next()%></td>
		    </tr>

			<%
		}%>
		</table>
		<%
		}
else if (request.getAttribute("query") == "query3"){
		%>
		<table width="59%" border="1">
	<tr>
		<td>Crime</td>
			<td>Count</td>
			
	</tr>
	<%
				ArrayList<String> rows_1 = new ArrayList<String>();
					

					if (request.getAttribute("list") != null) {

						rows_1 = (ArrayList) request.getAttribute("list");

					}
					
					Iterator iterator = rows_1.iterator();
					while (iterator.hasNext()) {
			%>
			<tr>
			<td><%=iterator.next()%></td>
			<td><%=iterator.next()%></td>
			
		    </tr>

			<%
		}%>
		</table>
		<%
		}
else if (request.getAttribute("query")=="query4"){
		%>
		
		<table width="59%" border="1">
	<tr>
		<td>Area Name</td>
			
	</tr>
	<%
				ArrayList<String> rows_1 = new ArrayList<String>();
					

					if (request.getAttribute("list") != null) {

						rows_1 = (ArrayList) request.getAttribute("list");

					}
					
					Iterator iterator = rows_1.iterator();
					while (iterator.hasNext()) {
			%>
			<tr>
			<td><%=iterator.next()%></td>
			
		    </tr>

			<%
		}%>
		</table>
		<%
		}%>
		</div>
</body>
</html>