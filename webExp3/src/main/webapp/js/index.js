// 网页加载完成后执行function里面的代码
$(function (){
    //定时器，每隔一秒刷新一次
    setInterval(function () {
        const date = new Date()
        $("#time").html('当前系统时间：' + date.getFullYear() + '-' + (date.getUTCMonth() + 1) + '-' + date.getUTCDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds())
    },1000)
    //加载文章
    load_article()


    $("#login").click(function (){
        const username = $("#username").val();
        const password = $("#password").val();
        if(username === "" || password === "" || username === undefined || password === undefined){
            alert("请输入用户名密码")
            return
        }
        $.post({
            url:'./user/login',
            data:{
                username:username,
                password:password
            },
            dataType:'json',
            success:function (data){
                if(data.status === 1){
                    $("#login_box").attr("hidden",true);
                    $("#has_login_box").attr("hidden",false);
                    $("#user_info").html(username + "，欢迎您！");
                    get_v_l_time();
                    $("embed")[0].stop()
                }else{
                    alert(data.msg);
                }
            }
        })
    })

    $("#exit").click(function (){
        if(confirm("确认退出？")){
            $("#login_box").attr("hidden",false);
            $("#has_login_box").attr("hidden",true);
            $("#username").val("");
            $("#password").val("");
            $("embed")[0].play()
        }
    })
})


function load_article(){
    const ul = $("#all_article");
    $.get({
        url:'./article/list',
        success:function (data){
            const dat = data.data;
            dat.forEach(function (da){
                let html = '<li>\n' +
                    '<div class="list_article">\n' +
                    '<a href="./article/' + da.id + '" target="_blank" class="name_article">' + da.title +'</a>\n' +
                    '</div>\n' +
                    '</li>';
                ul.append(html);
            })
        },
        dataType:'json'
    });
    //访问次数+1
    $.get({url:'./info/visitTimePlusOne'});
    //获取访问次数
    get_v_l_time();


}

function get_v_l_time() {
    $.get({
        url:'./info/get',
        dataType:'json',
        success:function (data){
            if(data.status === 1){
                $("#v_l_time").html("主页访问次数：" + data.data.visitTime + '&ensp;&ensp;&ensp;登录次数：' + data.data.loginTime);
            }
        }
    });
}

