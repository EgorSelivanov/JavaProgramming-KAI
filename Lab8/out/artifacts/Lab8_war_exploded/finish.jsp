<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18.05.2022
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Финишная страница</title>
</head>
<body>
    <jsp:useBean id="mybean" scope="session" class="main.java.BeanComponent" />
    <%
        mybean.setStrWithNumbers(request.getParameter("array"));
        mybean.processArray();
    %>
    <h3>
        <table border="3">
        <tr> <td>Введенный массив</td> <td>Обработанный массив</td>
        <tr> <td>${mybean.strWithNumbers}</td> <td>${mybean.zerosAndOnes}</td>
        </table>
    </h3>
    <a href="main.jsp"> Возврат на главную страницу</a>
</body>
</html>
