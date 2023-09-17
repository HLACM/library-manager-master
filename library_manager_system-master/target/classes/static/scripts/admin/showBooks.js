layui.use(['form', 'element','layer'], function () {
    let form = layui.form;
    let element = layui.element;
    let layer = layui.layer;
});

$(document).ready(function () {

    //给选择框赋值
    findAllBookCategory();

    //检查能否再点击上一页，下一页
    let lab1 = $("#lab1").html().trim();//获取当前页码
    let lab2 = $("#lab2").html().trim();//获取总页码


    $("#prePage").click(function () {
        if (lab1 == 1) {
            layer.msg("已经是第一页了!", {icon: 7});
            return false;
        }
        return true;
    });
    $("#nextPage").click(function () {
        if (lab1 == lab2) {
            layer.msg("已经是最后一页了!", {icon: 7});
            return false;
        }
        return true;
    });
});

// 定义函数：查询所有书籍分类
function findAllBookCategory() {
    $.ajax({
        async: false, // 同步请求
        type: "post", // 请求类型为POST
        url: "/findAllBookCategory", // 请求的URL
        dataType: "json", // 响应数据类型为JSON
        success: function (data) { // 请求成功的回调函数
            console.log(data); // 在控制台输出数据

            $("select[name='bookCategory']").empty(); // 清空名为 "bookCategory" 的选择框
            $("select[name='bookCategory']").append('<option value="">——请选择——</option>'); // 添加一个默认的选项
            for (let i = 0; i < data.length; i++) { // 遍历获取的数据
                let html = '<option value="' + data[i].categoryId + '">'; // 创建一个option标签，并设置value属性为categoryId
                html += data[i].categoryName + '</option>'; // 设置option标签的显示文本为categoryName
                $("select[name='bookCategory']").append(html); // 将option标签添加到选择框中
            }
        },
        error: function (data) { // 请求失败的回调函数
            alert(data.result); // 弹出错误信息
        }
    });
};
