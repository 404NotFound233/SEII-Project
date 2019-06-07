$(document).ready(function() {
    

    getAdminList();

function getAdminList() {
        getRequest(                                           //拉取管理员列表
                        '/user/admin/list',
                        function(res) {
                            var data = res.content || [];
                            adminList = data.map(function (item) {
                                return item.username;
                            });
                            var i=0;
                            var isAdmin = false;
                        for (i=0;i<adminList.length;++i){
                            if (adminList[i] == sessionStorage.getItem('username')) {
                                isAdmin = true;
                            }
                        }

                        if (isAdmin) {
                            sessionStorage.setItem('role', 'admin');
                            window.location.href = "/admin/movie/manage"
                        }

                        },
                function (error) {
                alert(JSON.stringify(error));
            }
                    );
    }
});