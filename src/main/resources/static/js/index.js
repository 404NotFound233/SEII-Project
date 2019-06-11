$(document).ready(function () {

    //文档就绪函数，避免在完全加载之前运行jquery

    $("#login-btn").click(function () {
        var formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            //不符合输入格式时
            return;
        }

        postRequest(
            '/login',
            formData,
            function (res) {
                if (res.success) {
                    sessionStorage.setItem('username', formData.username);
                    sessionStorage.setItem('id', res.content.id);
                    if (formData.username == "root") {
                        sessionStorage.setItem('role', 'admin');
                        window.location.href = "/admin/movie/manage"
                        //管理员登陆界面
                    } else {
                        sessionStorage.setItem('role', 'user');
                        window.location.href = "/user/home"
                        //普通用户登陆界面
                    }
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });
    });

    function getLoginForm() {
        return {
            username: $('#index-name').val(),
            password: $('#index-password').val()
            //通过.val()获取输入字段的值，达到get的目的
        };
    }

    function validateLoginForm(data) {
        var isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
            //将首个匹配元素的css改掉
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }
});