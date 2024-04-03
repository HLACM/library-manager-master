layui.use(['form', 'element', 'layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
    // 事件监听事件，监听按钮
    form.on('submit(btn_addUser)', function (data) {
        // 执行添加用户
        addUser();
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});


//添加用户的ajax方法
function addUser() {
    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: 'post',
        // url地址
        url: '/addUser',
        /*
            发送给服务器的数据
            serialize方法将表单数据转换为可以发送给服务器的格式
            形式为：userName=user1&userPwd=123456&UserEmail=7056547%40qq.com
            其中userName是标签的name属性，user1是标签的value属性
         */
        data: $('#addUserForm').serialize(),
        // 服务器响应成功
        success: function (data) {
            // 弹出提示框，添加成功
            layer.msg("添加成功", {icon: 1, time: 1500});

            // 添加成功后重新加载页面，1.5s后重新加载
            setTimeout(function () {
                location.reload();
            }, 1500)
        },
        // 服务器响应失败
        error: function (data) {
            // 弹出添加失败提示框
            layer.msg("添加失败", {icon: 2});
        }
    });
};