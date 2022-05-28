<%--
  Created by IntelliJ IDEA.
  User: Prince
  Date: 2021/5/22
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看文章</title>
    <!--Bootstrap-->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-3.5.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>

    <!--引入css-->
    <link rel="stylesheet" href="../css/article.css">
    <!--引入js-->
    <script src="../js/article.js"></script>

    <link rel="Shortcut Icon" href="../img/icon.png" type="image/x-icon" />

</head>
<body>
<div class="container" id="rootPanel">
    <!--当前系统时间-->
    <div class="row">
        <div class="col">
            <div id="time" style="color: red;font-weight: bold;font-size: 20px"></div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div><label id="title">${article.title}</label></div>
        </div>
    </div>

    <div>
        <div class="col">
            <div id="context">${article.content}</div>
        </div>
    </div>

</div>

</body>
</html>
