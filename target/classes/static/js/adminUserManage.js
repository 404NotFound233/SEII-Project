$(document).ready(function() {
    
    //查询角色
    $('#user-search-btn').click(function (){
        var username = $("#user-search-input").val();
        if (username == '') {
            $('#search-name-prompt').hide();
            $('#search-name-return').hide();
            $('#search-password-prompt').hide();
            $('#search-password-return').hide();
            alert("查询的用户名不能为空！");
        }
        else if (username.length < 4 || username.length > 10) {
            $('#search-name-prompt').hide();
            $('#search-name-return').hide();
            $('#search-password-prompt').hide();
            $('#search-password-return').hide();
            $("#user-search-input").val("");
            alert("用户名长度应在4-10位之间！");
         }
        else {
            getRequest(
                '/user/search' + '/' + username,
                function(res) {
                    var data = res.content || [];
                    if (data == '') {
                        alert("查询的用户不存在！");
                        $('#search-name-prompt').hide();
                        $('#search-name-return').hide();
                        $('#search-password-prompt').hide();
                        $('#search-password-return').hide();
                        $("#user-search-input").val("");
                    }
                    else {
                        var password = data.map(function (item) {
                            return item.password;
                         });

                         $('#search-name-return').html(username);
                         $('#search-password-return').html(password);
                         $('#search-name-prompt').show();
                         $('#search-name-return').show();
                         $('#search-password-prompt').show();
                         $('#search-password-return').show();
                         $("#user-search-input").val("");
                    }
                },
                function (error) {
                alert(JSON.stringify(error));
            }
            );
        }
    });


    //添加角色
     $('#user-add-btn').click(function (){
         var username = $("#user-add-name-input").val();
         var usertype = $("#user-add-type-input").val();
         var userpassword = $("#user-add-password-input").val();
         var userconfirm = $("#user-add-password-confirm").val();
         //验证两次密码的一致性
         if (username.length < 4 || username.length > 10) {
             alert("设置的用户名应在4-10位！");
             $("#user-add-name-input").val("");
         }
         else if (userpassword.length < 6 || userpassword.length > 12) {
             alert("设置的用户密码应在6-12位！");
             $("#user-add-password-input").val("");
             $("#user-add-password-confirm").val("");
         }
         else if (userpassword != userconfirm) {
             alert("两次输入的密码不一致！");
             $("#user-add-password-input").val("");
             $("#user-add-password-confirm").val("");
         }
         else {
             //需要对添加角色的类型进行判断，观众不需要加admin，管理员需要
             if (usertype == "admin") {
                 postRequest(
                 '/user/admin/add',
                 username,
                 function (res) {
                     if(res.success) {
                         alert("该用户名已被添加为管理员！");
                     }
                     else {
                         alert(res.message);
                     }
                 },
                  function (error) {
                      alert(JSON.stringify(error));
                    }
             );
             }
             //确认全部完成，开始添加用户名
             postRequest(
                 '/user/add',
                 {username:username,password:userpassword},
                 function (res) {
                     if(res.success) {
                         alert("角色添加成功");
                         $("#user-add-name-input").val("");
                         $("#user-add-type-input").val("admin");
                         $("#user-add-password-input").val("");
                         $("#user-add-password-confirm").val("");
                     }
                     else {
                         alert(res.message);
                     }
                 },
                  function (error) {
                      alert(JSON.stringify(error));
                    }
             );
         }
     });

     //删除角色
     $('#user-delete-btn').click(function (){
         var username = $("#user-delete-input").val();
         if (username == '') {
             alert("用户名不能为空！");
         }
         else if (username.length < 4 || username.length > 10) {
             alert("用户名长度应在4-10位之间！");
             $("#user-delete-input").val("");
         }
         else {
                  deleteRequest(
                    '/user/delete',
                    username,
                    function(res) {
                         if(res.success) {
                         alert("角色删除成功");
                         $("#user-delete-input").val("");
                     }

                     else {
                         alert(res.message);
                     }
                    },
                    function (error) {
                alert(JSON.stringify(error));
            }
                );
                //如果是管理员也需要删除admin
                  deleteRequest(
                    '/user/admin/delete',
                    username,
                    function(res) {
                        if(res.success) {
                            alert("管理员身份删除成功");
                        }
                     else {
                         alert(res.message);
                     }
                    },
                    function (error) {
                alert(JSON.stringify(error));
            }
                             );
            }
     });


     //修改角色
     $('#user-change-btn').click(function (){
         var preusername = $('#user-change-name-input').val();
         var username = $('#user-changed-name-input').val();
         var password = $('#user-changed-password-input').val();
         var confirm = $('#user-changed-password-confirm').val();
         if (preusername == '') {
             alert("待改用户名不能为空！");
             $('#user-change-name-input').val("");
         }
         else if (preusername.length < 4 || preusername.length > 10) {
             alert("待改用户名长度应在4-10位之间！");
             $('#user-change-name-input').val("");
         }
         else if (username == '') {
             alert("新的用户名不能为空！");
             $('#user-changed-name-input').val("");
         }
         else if(username.length < 4 || username.length > 10) {
             alert("新的用户名长度应在4-10位之间！");
             $('#user-changed-name-input').val("");
         }
         else if(password.length < 6 || password.length > 12) {
             alert("新的密码长度应在6-12位之间！");
             $('#user-changed-password-input').val("");
             $('#user-changed-password-confirm').val("");
         }
         else if(password != confirm) {
             alert("两次输入的密码不一致！");
             $('#user-changed-password-input').val("");
             $('#user-changed-password-confirm').val("");
         }
         else {
                postRequest(
                    '/user/change',
                    {preusername:preusername,username:username,password:password},
                    function(res) {
                         if(res.success) {
                         alert("角色修改成功");
                         $("#user-change-name-input").val("");
                         $("#user-changed-name-input").val("");
                         $('#user-changed-password-input').val("");
                         $('#user-changed-password-confirm').val("");
                     }
                     else {
                         alert(res.message);
                     }
                    },
                    function (error) {
                alert(JSON.stringify(error));
            }
                );
            }
     });


});