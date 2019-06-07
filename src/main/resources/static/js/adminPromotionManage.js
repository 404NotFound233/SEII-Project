var movieList=[];
var init_select_movie=true;
//ES6新api 不重复集合 Set
var selectedMovieIds = new Set();
var selectedMovieNames = new Set();
$(document).ready(function() {

    getAllMovies();

    getActivities();

    function getActivities() {
        getRequest(
            '/activity/get',
            function (res) {
                var activities = res.content;
                activities.forEach(function (activity) {
                    $('#give-coupon-input').append("<option value="+ activity.coupon.id +">"+activity.coupon.name+"</option>");
                });
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
    
    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            var movieDomStr = "";
            if(activity.movieList.length){
                activity.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            activitiesDomStr+=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.name+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>活动时间："+formatDate(new Date(activity.startTime))+"至"+formatDate(new Date(activity.endTime))+"</span>" +
                "       </div>" +
                "       <div class='activity-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>优惠券："+activity.coupon.name+"</span>" +
                "        <span class='title'>满"+activity.coupon.targetAmount+"减<span class='error-text title'>"+activity.coupon.discountAmount+"</span></span>" +
                "        <span class='gray-text'>"+activity.coupon.description+"</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                movieList = res.content;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

    $("#activity-form-btn").click(function () {
       var form = {
           name: $("#activity-name-input").val(),
           description: $("#activity-description-input").val(),
           startTime: $("#activity-start-date-input").val(),
           endTime: $("#activity-end-date-input").val(),
           movieList: [...selectedMovieIds],
           couponForm: {
               description: $("#coupon-name-input").val(),
               name: $("#coupon-description-input").val(),
               targetAmount: $("#coupon-target-input").val(),
               discountAmount: $("#coupon-discount-input").val(),
               startTime: $("#activity-start-date-input").val(),
               endTime: $("#activity-end-date-input").val()
           }
       };

        postRequest(
            '/activity/publish',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

       $("#coupon-form-btn").click(function () {
           var payTarget = $('#pay-target-input').val();
           var couponId = $('#give-coupon-input').val();
           var userIdList;

           getRequest(
               '/coupon/up/' + {payTarget},
               function(res) {
                   userIdList = res.content; 
               },
               function (error) {
                alert(JSON.stringify(error));
            }
           );

           var form = {userIdList:userIdList,couponId:couponId};
           postRequest(
               '/coupon/give',
               form,
               function(res) {
                   if (res.success) {
                        $("#couponModal").modal('hide');
                       alert("优惠券发放成功！");
                   }  else {
                    alert(res.message);
                }
               },
               function (error) {
                alert(JSON.stringify(error));
            }
           );

       });


        get_ac_movie();
    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();

        if(init_select_movie){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
            init_select_movie=false;
        }

        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
            for(var i=0;i<movieList.length;i++){
                var movieVO=movieList[i];
                selectedMovieIds.add(movieVO.id);
                selectedMovieNames.add(movieVO.name);
            }
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    function get_ac_movie(){
        getRequest(
                    '/movie/all/exclude/off',
                    function (res) {
                        movieList = res.content;
                        for(var i=0;i<movieList.length;i++){
                            var movieVO=movieList[i];
                            selectedMovieIds.add(movieVO.id);
                            selectedMovieNames.add(movieVO.name);
                        }
                        renderSelectedMovies();
                    },
                    function (error) {
                        alert(error);
                    }
             );
        }

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }
});