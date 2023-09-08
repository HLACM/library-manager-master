layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {
    // 点击修改信息按钮触发
    $('#modify-user').click(function () {
        //弹出层
        layer.open({
            type: 1 //Page层类型，页面层
            , skin: 'layui-layer-molv' //弹层皮肤样式
            , area: ['380px', '270px'] //弹出层大小
            , title: ['修改信息', 'font-size:18px'] //弹出层标题
            , btn: ['保存', '取消'] //弹出层底部按钮
            , shade: 0 //遮罩透明度。不显示遮罩层
            , content: $("#window") //弹出层的内容
            // yes参数，点击第一个按钮后执行方法
            , yes: function () {
                // 执行更新用户信息
                updateUser();
            }
        });
    });
});
function updateUser() {
    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: 'post',
        // url地址
        url: '/updateUser',
        // 发送给服务器的数据，形式为：userName=user1&UserEmail=7056547%40qq.com&userPwd=123456
        data: $('#updateUserForm').serialize(),
        // 请求响应成功
        success: function (data) {
            // 弹出修改成功框
            layer.alert('修 改 成 功', {icon: 1}, function () {
                // 修改成功后，重新加载页面，显示修改后的内容
                location.reload();
            });
        },
        // 请求响应失败，弹出修改失败
        error: function (data) {
            layer.alert("修改失败");
        }
    });
};