<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2 style="text-align:center">Meals</h2>
<section>
    <div style="text-align:center">
        <a href="meals?action=add"><button type="submit">Add meal</button></a>
    </div>
    <br>
    <table border="1" cellpadding="8" cellspacing="0" style="margin: auto">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
            <c:set var="color" value="${meal.excess ?  'red' : 'green'}" />
            <tr style="color:${color}">
                <td>${DateUtil.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=edit">Update</a></td>
                <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <hr>
</section>
</body>
</html>