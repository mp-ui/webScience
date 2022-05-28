//网页加载完成之后执行
$(function () {

    //定时器，每隔一秒刷新一次
    setInterval(function () {
        const date = new Date()
        $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
    },1000)
})
