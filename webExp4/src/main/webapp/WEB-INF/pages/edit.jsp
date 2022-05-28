<%--
  Created by IntelliJ IDEA.
  User: Prince
  Date: 2021/5/29
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>新增文章</title>
    <!--Bootstrap-->
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/jquery-3.5.1.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>

    <!--引入css-->
    <link rel="stylesheet" href="../../css/create.css">
    <!--引入js-->
    <script src="../../js/create.js"></script>
    <link rel="Shortcut Icon" href="../../img/icon.png" type="image/x-icon" />
</head>
<body>
    <input hidden value="${article.id}" id="id">
    <div class="container">
        <div class="row">
            <div class="col">
                <label>
                    文章标题
                    <input type="text" id="title" value="${article.title}" />
                </label>
            </div>
            <div class="col">
                <button id="edit" class="btn btn-primary btn-sm">保存</button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label>
                    <div>内容</div>
                    <textarea id="content">${article.content}</textarea>
                </label>
            </div>
        </div>

    </div>
</body>
</html>
