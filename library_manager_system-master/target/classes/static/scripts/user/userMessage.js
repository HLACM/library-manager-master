layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {
    // 点击修改信息按钮触发
    $('#modify-user').click(function () {
        //页面层
        layer.open({
            type: 1 //Page层类型
            , skin: 'layui-layer-molv'
            , area: ['380px', '270px']
            , title: ['修改信息', 'font-size:18px']
            , btn: ['取消', '保存']
            , shade: 0 //遮罩透明度
            , content: $("#window")
            , yes: function () {
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