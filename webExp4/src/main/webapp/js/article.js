//网页加载完成之后执行
$(function () {

    //定时器，每隔一秒刷新一次
    setInterval(function () {
        const date = new Date()
        $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
    },1000);
    $("#del").click(function (){
        if(confirm("确定要删除吗？")){
            $.get({
                url:'./delete',
                data:{
                    id:$("#id").val()
                },
                success:function (data){
                    if(data.status === 1){
                        alert("删除成功！")
                    }else{
                        alert("删除失败！")
                    }
                },
                error:function (){
                    alert("删除失败！")
                }
            })
        }
    })
})
