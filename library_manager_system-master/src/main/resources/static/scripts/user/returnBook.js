layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {
    // 点击还书按钮触发
    $("#btn1").click(function () {
        /*
           id = bookid的标签是输入书籍编号的输入框
           val方法，获取输入框的值
           trim方法，去除两端的空格
        */
        let bookId = $("#bookId").val().toString().trim();
        /*
         isNaN方法，判断bookid是否是有效数字，是则为false
         判断书籍编号是否为有效数字
      */
        if (bookId === null || bookId === '' || isNaN(bookId)) {
            layer.alert("请正确输入书籍编号");
            return false;
        }
        // bookid为有效数字，调用还书方法
        returnBook(bookId);

    });
});

function returnBook(bookId) {
    $.ajax({
        //设置为同步访问
        async: false,
        // psot请求方式
        type: "post",
        // url路径
        url: "/userReturnBook",
        // 服务器返回的数据类型
        dataType: "json",
        // 发送到服务器的数据key value
        data: {bookId: bookId},
        /*
            请求响应成功，判断服务器返回的数据是true还是false
            true则可以借书
         */
        success: function (data) {

            if (data.toString() == "true") {
                layer.msg('还书成功!!', {icon: 6, time: 2000});
                $("#bookId").val('');
            } else {
                layer.msg('还书失败!', {icon: 7, time: 2000});
                $("#bookId").val('');
            }

        },
        // 请求没有响应，返回报错信息
        error: function (data) {
            alert(data.result);
        }
    });
};