<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <title>我的订单</title>
    <%--静态包含base、css、jQuery--%>
    <%@include file="/pages/common/head.jsp" %>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif">
    <span class="wel_word">订单详情</span>
    <%--静态包含登录菜单--%>
    <%@include file="/pages/common/login_success_menu.jsp" %>

</div>

<div id="main">

    <div style="text-align: center">
        <h3>订单号：${requestScope.orderItems.get(0).orderId}</h3>
        <a href="orderServlet?action=showMyOrder&userId=${sessionScope.user.id}">返回订单</a>

    </div>
    <table>
        <tr>
            <td>书号</td>
            <td>书名</td>
            <td>总数</td>
            <td>单价</td>
            <td>总价</td>
        </tr>

        <c:forEach items="${requestScope.orderItems}" var="orderItem">
            <tr>
                <td>${orderItem.id}</td>
                <td>${orderItem.name}</td>
                <td>${orderItem.count}</td>
                <td>${orderItem.price}</td>
                <td>${orderItem.totalPrice}</td>
            </tr>
        </c:forEach>

    </table>


</div>

<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
</div>
</body>
</html>