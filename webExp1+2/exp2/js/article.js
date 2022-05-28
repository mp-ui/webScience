//网页加载完成之后执行
$(function () {
    const file = $("#file");
    //点击按钮后触发事件
    $("#selectFile").click(function () {
        console.log(file.val())
        if (file.val() === '') {
            alert('你还没选择文件呢！！请点击左边的按钮选择文件')
            return;
        }
        //获取form对象
        const formData = new FormData($("#form")[0])

        //发送ajax请求到后端
        $.ajax(
            {
                type: 'post',
                url: 'http://120.79.62.112:8989/readFile/txt',
                // url: 'http://127.0.0.1:8989/readFile/txt',
                data: formData,
                cache: false,
                processData: false,  //默认值为true，会转数据格式，上传不需要转，所以设置false
                contentType: false,  //contentType默认的值为：'application/x-www-form-urlencoded; charset=UTF-8，而文件上传一个是multipart/form-data，但是请求内容不只是文件上传。所有使用contentType:false
                dataType: 'json',	//后端返回到前端的数据类型是json
                success: function (data) {
                    const status = data.status;
                    const title = data.title;
                    const context = data.context;
                    if(status === 0 || status === undefined){
                        alert("失败！");
                        return;
                    }
                    alert("成功")
                    $("#title").html(title);
                    $("#context").html(context);
                },
                error:function (){
                    alert("失败！");
                }
            }
        )
    });
    //定时器，每隔一秒刷新一次
    setInterval(function () {
        const date = new Date()
        $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
    },1000)
})
