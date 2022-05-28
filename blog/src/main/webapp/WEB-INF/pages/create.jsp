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
    <%@ include file="include.jsp" %>
</head>

<style>
    label{
        width: 100%;
    }

    #title{
        width: 60%;
    }

    #content{
        width: 95%;
        height: 800px;
    }
</style>

<body>
    <div class="container">
        <div class="row">
            <div class="col">
                <label>
                    文章标题
                    <input type="text" id="title" />
                </label>
            </div>
            <div class="col">
                <button id="create" class="btn btn-primary btn-sm">新增文章</button>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label>
                    <div>内容</div>
                    <textarea id="content"></textarea>
                </label>
            </div>
        </div>

    </div>
</body>
</html>

<script>
    $(function () {
        $("#create").click(function () {
            let title = $("#title").val();
            let content = $("#content").val()
            if (title === undefined || content === undefined || title === '' || content === '') {
                alert("请输入内容");
                return;
            }
            title = title.trim()
            content = content.trim()
            if (title === '' || content === '') {
                alert("请输入内容");
                return;
            }
            $.post({
                url: './create',
                data: {
                    title: title,
                    content: content
                },
                success: function (data) {
                    alert(data.msg);
                    if (data.status === 1) {
                        const aid = data.data.article_id;
                        if(aid !== undefined) {
                            location.replace("${pageContext.request.contextPath}/article/" + aid)
                        }
                    }
                },
                error: function () {
                    alert("未知错误！")
                }
            });
        })

    })
</script>