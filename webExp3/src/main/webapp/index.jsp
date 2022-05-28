<%--
  Created by IntelliJ IDEA.
  User: Prince
  Date: 2021/5/22
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人博客--文章列表</title>
    <!--Bootstrap-->
    <link rel="stylesheet" href="./css/bootstrap.min.css">
    <script src="./js/jquery-3.5.1.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>

    <!--引入css-->
    <link rel="stylesheet" href="./css/index.css">
    <!--引入js-->
    <script src="./js/index.js"></script>

    <link rel="Shortcut Icon" href="./img/icon.png" type="image/x-icon" />

    <embed src="./music/bgm.mp3" hidden="true" autostart="true" loop="true">
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
            <div id="v_l_time" style="color: blue;font-weight: bold;font-size: 16px"></div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div id="title" style="font-size: 35px;font-weight: bold">文章列表</div>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <div id="list">
                <ul id="all_article">
                </ul>
            </div>
        </div>
    </div>

    <div class="row" id="login_box">
        <div class="col">
            <div style="text-align: center !important;">
                <input type="text" id="username" placeholder="username" width="200px">
                <input type="password" id="password" placeholder="Password" width="200px">
                <button class="btn btn-primary btn-sm" id="login">login</button>
            </div>
        </div>
    </div>
    <div class="row" id="has_login_box" hidden>
        <div class="col">
            <div>
                <span id="user_info">XXX，欢迎您</span>
                <button class="btn btn-danger btn-sm" id="exit">退出登录</button>
            </div>
        </div>
    </div>



</div>
</body>
</html>
