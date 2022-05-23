<%--
  Created by IntelliJ IDEA.
  User: Beetle
  Date: 18.05.2022
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
    <jsp:useBean id="mybean" scope="session" class="main.java.BeanComponent" />
    <%
        mybean.setCountOfReload(mybean.getCountOfReload() + 1);
    %>
    <form name="Input form" action="finish.jsp">
        <input type="text" name="array" />
        <input type="submit" value="Преобразовать" name="button1" />
    </form>

    <h3>Счетчик на главной странице: ${mybean.countOfReload}</h3>
    <a href="index.jsp">Посмотреть задание</a>
</body>
</html>
