layui.use(['element', 'form', 'layer'], function () {
    let layer = layui.layer;
    let element = layui.element;
    let form = layui.form;
    // 表单监听事件，监听提交按钮
    form.on('submit(btn1)', function() {
        // 点击提交按钮后执行
        addBook();

        // console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value};获取单个值data.field["title"]
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
});

function addBook() {

    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: 'post',
        // url地址
        url: '/addBook',
        /*
           发送给服务器的数据
           serialize方法将表单数据转换为可以发送给服务器的格式
           形式为：userName=user1&UserEmail=7056547%40qq.com&userPwd=123456
           其中userName是标签的name属性，user1是标签的value属性
        */
        data: $('#addBookForm').serialize(),
        // 响应请求成功
        success: function (data) {
            layer.msg("添加成功", {time: 1500, icon: 1});
            // setTimeout方法，延迟一定时间后，执行回调函数
            // 此处1.5s后刷新页面
            setTimeout(function () {
                // 重新刷新页面
                location.reload();
            }, 1500)
        },
        // 响应请求失败
        error: function (data) {
            alert("添加失败");
        }
    });
};

// 页面加载完毕
$(document).ready(function () {

    //查找所有图书种类，并给select选择框赋值
    findAllBookCategory();

});

function findAllBookCategory() {
    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: "post",
        // url地址
        url: "/findAllBookCategory",
        // 从服务器接收的数据类型
        dataType: "json",
        // 响应请求成功
        success: function (data) {
            // 获取name为bookCategory的select标签
            let bookCategory = $("select[name='bookCategory']");
            // 添加初始显示的下拉选项
            bookCategory.append('<option value="">——请选择——</option>');
            // 遍历服务器返回的书籍种类集合
            for (let i = 0; i < data.length; i++) {
                // 获取单个书籍种类的信息,并设置下拉框的标签以及内容
                let html = '<option value="' + data[i].categoryId + '">';
                html += data[i].categoryName + '</option>';
                // 添加下拉框的每一个选项
                bookCategory.append(html);
            }
        },
        // 响应请求失败
        error: function (data) {
            alert(data.result);
        }
    });
};













