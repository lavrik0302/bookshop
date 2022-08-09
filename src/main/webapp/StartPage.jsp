<%--
  Created by IntelliJ IDEA.
  User: alexey.lavrenchuk
  Date: 01.08.2022
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to BookShop</title>
</head>
<body>
<h1>Welcome to BookShop</h1>
<h1>Use /person : </h1>

<h4>GET for finding person with orders by personId</h4>
<h4>POST for adding new person</h4>
<h4>PUT for editing person's information</h4>
<h4>DELETE for deleting person</h4>

<h1>Use /book :</h1>

<h4>GET for finding book by bookId</h4>
<h4>POST for adding new book</h4>
<h4>PUT for editing book's information</h4>
<h4>DELETE for deleting book</h4>

<h1>Use /bookInCart : </h1>

<h4>GET for finding books in cart by cartId</h4>
<h4>POST for adding new book to cart</h4>
<h4>PUT for editing books count in cart</h4>
<h4>DELETE for deleting book from cart</h4>

<h1>Use /personOrder :</h1>

<h4>GET for finding books in order by orderId</h4>
<h4>POST for adding new person order from person's cart</h4>
<h4>PUT for editing statusId in person order</h4>
<h4>DELETE for deleting person order</h4>

</body>
</html>
