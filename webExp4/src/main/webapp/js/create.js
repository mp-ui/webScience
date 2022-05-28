$(function () {
    $("#create").click(function () {
        const title = $("#title").val();
        const content = $("#content").val()
        if (title === undefined || content === undefined || title === '' || content === '') {
            alert("请输入内容");
            return;
        }
        $.post({
            url: './create1',
            data: {
                title: title,
                content: content
            },
            success: function (data) {
                if (data.status === 1) {
                    alert("添加成功！")
                    if(data.articleId !== undefined) {
                        location.href = location.href.substr(0, location.href.lastIndexOf("/")) + "/" + data.articleId;
                    }
                } else {
                    alert("添加失败！")
                }
            },
            error: function () {
                alert("添加失败！")
            }
        });
    })
    $("#edit").click(function () {
        const title = $("#title").val();
        const content = $("#content").val()
        if (title === undefined || content === undefined || title === '' || content === '') {
            alert("请输入内容");
            return;
        }
        $.post({
            url: '../edit1',
            data: {
                id: $("#id").val(),
                title: title,
                content: content
            },
            success: function (data) {
                if (data.status === 1) {
                    alert("修改成功！")
                    location.href=location.href.replace("edit/","");
                } else {
                    alert("修改失败！")
                }
            },
            error: function () {
                alert("修改失败！")
            }
        });
    })

})