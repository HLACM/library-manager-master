layui.use(['form','element','layer'], function () {
    let form = layui.form;
    let element = layui.element();
    let layer = layui.layer;
});

$(document).ready(function () {

    // 按钮监听事件，提交按钮
    $("#btn_addBookCategory").click (function () {
        alert(1);
        // 点击提交按钮后执行
        addBookCategory();
        // 阻止表单跳转
        return false;
    });

    //检查能否再点击上一页，下一页
    /*
        html方法，返回html内容
        trim方法，去除前后的空格
     */
    let lab1 = $("#lab1").html().trim();//获取当前页码
    let lab2 = $("#lab2").html().trim();//获取总页码
    // 点击上一页按钮触发
    $("#prePage").click(function () {
        // 如果当前页码为1
        if (lab1 == 1) {
            // 弹出提示信息
            layer.msg("已经是第一页了!", {icon: 7});
            // 不执行超链接跳转
            return false;
        }
        // 执行超链接跳转
        return true;
    });
    // 点击下一页按钮触发
    $("#nextPage").click(function () {
        // 如果当前已经是最后一页
        if (lab1 == lab2) {
            layer.msg("已经是最后一页了!", {icon: 7});
            // 不执行超链接跳转
            return false;
        }
        // 执行超链接跳转
        return true;
    });

    //点击删除按钮后删除一行
    $(".btn_deleteCategory").click(function () {

        // 获取要删除选项对应的 id;
        // 因为此处的按钮value值就是当前种类的id，所以直接&(this)
        let that = $(this);
        // confirm方法创建一个对话框
        layer.confirm('确认删除?', {
            btn: ['确认', '取消'] //按钮
        //     点击确认按钮后执行回调函数
        }, function () {
            // val方法，获取当前标签元素值，也就是value值的种类id
            let bookCategoryId = that.val();
            // 执行方法，删除该种类
            deleteBookCategoryById(bookCategoryId);
            // 通过parent方法找到父元素，直到该行，remove删除该行
            that.parent().parent().remove();
            // 弹出删除成功提示框
            layer.msg("删除成功", {icon: 1, time: 1000});

            setTimeout(function () {
                // 没看出来有什么作用
                // 关闭所有 layer选项框
                parent.layer.closeAll();
            }, 1000)

        });

    });
});


//ajax添加种类
function addBookCategory() {
    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: "post",
        // url地址
        url: "/addBookCategory",
        // 从服务器接收的数据类型
        dataType: "json",
        /*
            发送给服务器的数据
            serialize方法将表单数据转换为可以发送给服务器的格式
            形式为：categoryName=category&pageNum=1
            其中categoryName是标签的name属性，category是标签的value属性
         */
        data: $("#addBookCategoryForm").serialize(),
        // 服务器响应成功
        success: function (data) {
            // 服务端判断结果为成功
            if (data.toString() == "true") {
                // 弹出添加成功信息
                layer.msg("添加成功!", {icon: 1, time: 1500});

                // 1500ms后 重新加载页面 , 将更改后的内容重新加载到页面
                setTimeout(function () {
                    location.reload();
                }, 1500);
            }
            // 判断不成功
            else {
                // 弹出添加失败框
                layer.msg("添加失败!", {icon: 2, time: 1500});
            }
        },
        // 服务端响应失败
        error: function (data) {
            layer.msg("添加失败!", {icon: 2, time: 1500});
        }
    });
};

//ajax删除种类
function deleteBookCategoryById(bookCategoryId) {
    $.ajax({
        // 同步请求
        async: false,
        // 请求方式
        type: "post",
        // url地址
        url: "/deleteCategory",
        // 从服务端接收的数据类型
        dataType: "json",
        // 传输的数据
        data: {bookCategoryId: bookCategoryId},
        // 服务端响应成功
        success: function (data) {

        },
        // 服务端响应失败
        error: function (data) {
            alert(data.result);
        }
    });
}

