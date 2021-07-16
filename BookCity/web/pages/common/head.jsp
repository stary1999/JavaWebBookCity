<%--
  Created by IntelliJ IDEA.
  User: stary
  Date: 2021/5/5
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"
    +request.getServerName()
    +":"
    +request.getServerPort()
    +request.getContextPath()
    +"/";
    pageContext.setAttribute("basePath",basePath);
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />

<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>