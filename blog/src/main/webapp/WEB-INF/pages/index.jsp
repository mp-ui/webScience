<%--
  Created by IntelliJ IDEA.
  User: Prince
  Date: 2021/6/1
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人博客--文章列表</title>
    <%@ include file="include.jsp" %>
</head>

<style>
    .row{
        margin-top: 10px;
        margin-bottom: 10px;
        padding-top: 10px;
        padding-bottom: 10px;
    }

    #list{
        margin-left: 10%;
        margin-right: 10%;
        text-align: left;
    }

    .list_article{
        margin-top: 10px;
        margin-bottom: 10px;
        text-align: left;
    }

    .name_article{
        font-size: 20px;
    }


</style>


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
                <div id="title" style="font-size: 35px;font-weight: bold">文章列表</div>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <input id="search_input" style="height: 40px;border-radius: 10px;padding-left: 10px;font-size: 25px;width: 400px;vertical-align: middle">
                <button id="search_btn" class="btn btn-success">搜索文章</button>
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

        <!--分页-->
        <div class="row">
            <div class="col">
                <span id="total">total: 0</span>
            </div>
            <div class="col">
                <div id="pagination">
                    <button class="btn btn-info btn-sm" id="lastPage">上一页</button>
                    <span id="nowPage">第1页</span>
                    <button class="btn btn-info btn-sm" id="nextPage">下一页</button>
                </div>
            </div>
            <%--每页限制条数--%>
            <div class="col">
                <div class="btn-group">
                    <button type="button" class="btn btn-info btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false" id="limit_show">
                        10条/页
                    </button>
                    <div class="dropdown-menu" id="limit_select">
                        <a class="dropdown-item" href="#">10条/页</a>
                        <a class="dropdown-item" href="#">20条/页</a>
                        <a class="dropdown-item" href="#">30条/页</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">全部</a>
                    </div>
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
                    <a class="btn btn-primary btn-sm" target="_blank" href="${pageContext.request.contextPath}/article/create" style="color: white">新增文章</a>
                    <button class="btn btn-danger btn-sm" id="exit">退出登录</button>
                </div>
            </div>
        </div>

    </div>

</body>
</html>

<script>
    let name = '';
    let page = 1;
    let limit = 10;
    $(function (){
        $.cookie.json = true; //开启json操作

        //定时器，每隔一秒刷新一次
        setInterval(function () {
            const date = new Date()
            $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
        },1000)

        //检查cookies
        {
            const user = $.cookie("user");
            if(user !== undefined && user.username !== undefined && user.password !== undefined){
                //利用cookie中的账号密码进行登录
                $.post({
                    url:'${pageContext.request.contextPath}/user/login',
                    data:{
                        username:user.username,
                        password:user.password
                    },
                    dataType:'json',
                    success:function (data){
                        if(data.status === 1){
                            $("#login_box").attr("hidden",true);
                            $("#has_login_box").attr("hidden",false);
                            const alias = data.data.alias;
                            const username1 = data.data.username;
                            if(alias === undefined || alias === ''){
                                $("#user_info").html(username1 + "，欢迎您！");
                            }else{
                                $("#user_info").html(alias + "，欢迎您！");
                            }
                            //$("embed")[0].stop()
                        }else{
                            $.removeCookie("user");
                        }
                    }
                })
            }
        }

        //加载文章
        load_article()

        //登录按钮
        $("#login").click(function (){
            const username = $("#username").val();
            const password = $("#password").val();
            if(username === "" || password === "" || username === undefined || password === undefined){
                alert("请输入用户名密码")
                return
            }
            $.post({
                url:'${pageContext.request.contextPath}/user/login',
                data:{
                    username:username,
                    password:password
                },
                dataType:'json',
                success:function (data){
                    if(data.status === 1){
                        $("#login_box").attr("hidden",true);
                        $("#has_login_box").attr("hidden",false);
                        const alias = data.data.alias;
                        const username1 = data.data.username;
                        if(alias === undefined || alias === ''){
                            $("#user_info").html(username1 + "，欢迎您！");
                        }else{
                            $("#user_info").html(alias + "，欢迎您！");
                        }
                        //$("embed")[0].stop()
                        //加入Cookie
                        $.cookie("user",{username:username,password:password},{expires: 30});
                    }else{
                        alert(data.msg);
                    }
                }
            })
        })

        //退出
        $("#exit").click(function (){
            if(confirm("确认退出？")){
                $("#login_box").attr("hidden",false);
                $("#has_login_box").attr("hidden",true);
                $("#username").val("");
                $("#password").val("");
                //$("embed")[0].play()
                //删除cookie
                $.removeCookie("user");
            }
        })

        //分页
        $("#lastPage").click(function (){
            page -= 1;
            load_article();
        })
        $("#nextPage").click(function (){
            page += 1;
            load_article();
        })

        //搜索
        $("#search_btn").click(function (){
            name = $("#search_input").val();
            page = 1;
            load_article();
        })

        //修改每页限制
        $("#limit_select a").each(function (){
            $(this).click(function (){
                $("#pagination").attr("hidden",false);
                const val = $(this).html();
                switch (val){
                    case "10条/页":
                        limit = 10
                        break;
                    case "20条/页":
                        limit = 20
                        break;
                    case "30条/页":
                        limit = 30
                        break;
                    default:
                        limit = undefined;
                        $("#pagination").attr("hidden",true);
                }
                page = 1;
                load_article();
                $("#limit_show").html(val);
            })
        })

    })


    //加载文章
    function load_article(){
        const ul = $("#all_article");
        $.get({
            url:'./article/lists',
            data:{
                name: name,
                page: page,
                limit: limit
            },
            success:function (data){
                const dat = data.data;
                let html = '';
                dat.forEach(function (da){
                    html += '<li>\n' +
                        '<div class="list_article">\n' +
                        '<a href="${pageContext.request.contextPath}/article/' + da.id + '" target="_blank" class="name_article">' + da.title +'</a>\n' +
                        '</div>\n' +
                        '</li>';
                })
                ul.html(html);
                $("#total").text("total: " + data.total);
                page = data.page;
                $("#nowPage").text("第" + data.page + "页");
            },
            dataType:'json'
        });

    }


</script>
