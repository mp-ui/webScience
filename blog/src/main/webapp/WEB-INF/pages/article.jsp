<%--
  Created by IntelliJ IDEA.
  User: Prince
  Date: 2021/5/22
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>查看文章</title>
    <%@ include file="include.jsp" %>

</head>

<style>
    /*文章标题*/
    #title{
        font-size: 35px;
        font-weight: bold;
    }

    #context{
        text-align: left;
    }

    #context div{
        text-align: left;
    }

    .container{
        padding-bottom: 30px;
    }

    /*评论*/
    .comment_row div{
        text-align: left;
    }

    .comment_row{
        padding-top: 10px;
        padding-bottom: 10px;
        margin-top: 10px;
        margin-bottom: 10px;
        margin-left: 10px;
    }

    .comment_name{
        font-weight: bold;
    }

    .comment_time{
        color: #6c757d;
        margin-left: 20px;
    }

    /*博主特定标识*/
    .comment_name_blogger{
        color: #005cbf;
    }



</style>

<body>
<div class="container">
    <input id="id" value="${article.id}" hidden>

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

    <div class="row">
        <c:if test="${user != null}">
            <div class="col">作者：${user.alias == null || user.alias == "" ? user.username : user.alias}</div>
        </c:if>
        <c:if test="${article.pageview != null && article.id != -1}">
            <div class="col">浏览量：${article.pageview}</div>
        </c:if>
        <c:if test="${ctime != null}">
            <div class="col">发布时间：${ctime}</div>
        </c:if>

    </div>

    <%--只有我是这篇文章的作者才会显示--%>
    <div class="row" hidden id="edit_del">
        <div class="col">
            <a target="_self" href="./edit/${article.id}" style="margin-right: 20px">编辑</a>
            <a href="#" id="del" style="margin-left: 20px">删除</a>
        </div>
    </div>


    <hr />

    <%--正文--%>
    <div class="row">
        <div class="col">
            <div id="context">${article.content}</div>
        </div>
    </div>

    <hr />

    <%--404页面不展示评论区--%>
    <c:if test="${article != null && article.id != -1}">
        <div class="row">
            <div class="col-11">
                <label style="width: 100%">
                    <textarea id="text" style="width: 100%;height: 80px"></textarea>
                </label>
            </div>
            <div class="col-1">
                <button class="btn btn-primary" id="handleComment" style="float: left">评论</button>
            </div>
        </div>

        <%--评论列表--%>
        <div class="row">
            <div class="col">
                <div id="countComments" style="font-weight: bold">评论列表(0条):</div>
                <div id="allComments">
<%--                <div class="comment_row">--%>
<%--                    <div>--%>
<%--                        <span class="comment_name">大哥</span>--%>
<%--                        <span class="comment_time">2021-6-2 9:50:07</span>--%>
<%--                    </div>--%>
<%--                    <div class="comment_text">--%>
<%--                        牛逼--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="comment_row">--%>
<%--                    <div>--%>
<%--                        <span class="comment_name">大哥</span>--%>
<%--                        <span class="comment_time">2021-6-2 9:50:07</span>--%>
<%--                    </div>--%>
<%--                    <div class="comment_text">--%>
<%--                        牛逼--%>
<%--                    </div>--%>
<%--                </div>--%>
                </div>
            </div>
        </div>
    </c:if>



</div>

</body>
</html>


<script>
    //网页加载完成之后执行
    $(function () {
        $.cookie.json = true;
        //加载评论区
        flushComment();
        //定时器，每隔一秒刷新一次
        setInterval(function () {
            const date = new Date()
            $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
        },1000);

        //检查cookies，判断这篇文章的作者是不是自己，如果是的话就显示编辑和删除
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
                            if(data.data.id === ${article.uid}){
                                $("#edit_del").attr("hidden",false);
                            }
                        }else{
                            $.removeCookie("user");
                        }
                    }
                })
            }
        }

        //删除
        $("#del").click(function (){
            if(confirm("你确定要删除吗？一旦删除将无法恢复！！！")){
                $.post({
                    url: './delete',
                    data: {
                        id:${article.id},
                    },
                    success: function (data) {
                        alert(data.msg);
                        if (data.status === 1) {
                            location.reload();
                        }
                    },
                    error: function () {
                        alert("未知错误！")
                    }
                });
            }
        })

        //评论
        $("#handleComment").click(function (){
            let text = $("#text").val()
            if(text === undefined || text === ''){
                alert("请输入评论内容");
                return;
            }
            text = text.trim();
            if(text === ''){
                alert("请输入评论内容");
                return;
            }
            $.post({
                url:'${pageContext.request.contextPath}/comment/comment',
                data:{
                    id: ${article.id},
                    text: text
                },
                success:function (data){
                    alert(data.msg);
                    if(data.status === 1){
                        //刷新评论区
                        flushComment();
                        //清空输入框
                        $("#text").val('');
                    }
                }
            })
        })
    })

    //刷新评论区
    function flushComment(){
        $.get({
            url:'${pageContext.request.contextPath}/comment/list',
            data:{
                aid:${article.id}
            },success:function (data){
                if(data.status === 1){
                    const comments = data.data;
                    if(comments !== null){
                        //逐一添加
                        let html = '';
                        comments.forEach(function (comment){
                            //展示的名称
                            let name = comment.username;
                            if(comment.alias !== undefined && comment.alias !== ''){
                                name = comment.alias;
                            }
                            html += '<div class="comment_row">'
                            html += '<div>'
                            //判断是不是博主
                            if(comment.uid === ${article.uid}){
                                html += '<span class="comment_name comment_name_blogger">'+ name + '(博主)' +'</span>'
                            }else{
                                html += '<span class="comment_name">'+ name +'</span>'
                            }
                            html += '<span class="comment_time">' + comment.ctime +'</span>'
                            html += '</div>'
                            html += '<div class="comment_text">'
                            html += comment.text;
                            html += '</div>'
                            html += '</div>'
                        })
                        //展示
                        $("#allComments").html(html);
                        //展示一共多少条评论
                        $("#countComments").html('评论列表('+ comments.length +'条):')
                    }
                }
            }
        })
    }
</script>