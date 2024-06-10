<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2 style="text-align:center">Edit Meals</h2>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>DateTime:</dt>
            <dd>
                <input type="datetime-local" name="dateTime" size=20 value="${DateUtil.format(meal.dateTime)}" placeholder="yyyy-MM-dd HH:mm">
            </dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><input type="text" required name="description" size=50 value="${meal.description}"></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><input type="text" required name="calories" size=50 value="${meal.calories}"></dd>
        </dl>
        <hr>
        <button type="submit">Save</button>
        <button type="button" onclick="window.history.back()">Cancel</button>
    </form>
</section>
</body>
</html>
