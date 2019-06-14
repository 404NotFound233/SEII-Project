$(document).ready(function() {
    
    getAdminList();

    getMovieList('');

    function getMovieList(keyword) {
        getRequest(
            '/movie/search?keyword='+keyword,
            function (res) {
                renderMovieList(res.content);
            },
             function (error) {
            alert(error);
        });
    }

    function renderMovieList(list) {
        $('.movie-on-list').empty();
        $('.movie-off-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            if (movie.status != 1 && new Date(movie.startDate)<=new Date()) {
                movieDomStr +=
                "<li class='movie-item-home'>" +
                "<a href='/user/movieDetail?id="+ movie.id +"'><img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/></a>" +
                 "<span class='primary-text-home'>" + movie.name + "</span>" +
                "</li>";
            }  
        });
        $('.movie-on-list').append(movieDomStr);
        var movieDomStr = '';
        list.forEach(function (movie) {
            if (movie.status != 1 && new Date(movie.startDate)>new Date()) {
                movieDomStr +=
                "<li class='movie-item-home'>" +
                "<a href='/user/movieDetail?id="+ movie.id +"'><img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/></a>" +
                 "<span class='primary-text-home'>" + movie.name + "</span>" +
                "</li>";
            }  
        });
        $('.movie-off-list').append(movieDomStr);

    }


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