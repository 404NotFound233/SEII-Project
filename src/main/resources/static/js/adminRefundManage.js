/*
感觉写的差不多了
还有一丢丢小Bug没调完
选电影的时候还有一些无法言说的问题
anyway加油吧
*/
var movieList=[];
var initstate=true;
var init_select_movie=true;
//ES6新api 不重复集合 Set
var selectedMovieIds = new Set();
var selectedMovieNames = new Set();
var c_movieList=[];
var c_init_select_movie=true;
var c_selectedMovieIds = new Set();
var c_selectedMovieNames = new Set();
$(document).ready(function() {

    getAllMovies();
    getRefunds();


//emm我觉得这个部分也要写的
//等我写掉点七七八八的东西再来写它
    //getRefunds();
//看起来get方法挺对的了
    function getRefunds() {
        var that=this;
            getRequest(
                '/refund/get',
                function (res) {
                    var refunds = res.content;
       if(refunds.length!=0){
       document.getElementById("c-refund-free-input").value=refunds[0].freetime;
       document.getElementById("c-refund-full-input").value=refunds[0].fulltime;
       document.getElementById("c-refund-discount-input").value=refunds[0].discount;}
                    renderRefunds(refunds);
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }

    function renderRefunds(refunds) {
        $(".content-refund").empty();
        var refundsDomStr = "";
        if(refunds.length!=0){
            $("#releaseBtn").attr("disabled", true);
            $("#changeBtn").attr("disabled", false);
        }
        refunds.forEach(function (refund) {
            var movieDomStr = "";//参与电影的dom
            if(refund.movieList.length){
                refund.movieList.forEach(function (movie) {
                    movieDomStr += "<li class='activity-movie primary-text'>"+movie.name+"</li>";
                });
            }else{
                movieDomStr = "<li class='activity-movie primary-text'>所有热映电影</li>";
            }

            refundsDomStr+=
                "<div class='refund-container'>" +
                "    <div class='refund-card card'>" +
                "       <div class='refund-line'>" +
                "           <span class='title'>"+"退票策略"+"</span>" +
                "           <span class='gray-text'>"+"退票策略包含退票时间、电影范围和退票范围"+"</span>" +
                "       </div>" +

                "       <div class='refund-line'>" +
                "           <span>允许全价退票期限：</span>" +
                "               <ul>"+refund.freetime+"天外"+"</ul>" +
                "       </div>" +
                "       <div class='refund-line'>" +
                "           <span>价格折算：</span>" +
                "               <ul>"+refund.discount+"</ul>" +
                "(在"+refund.fulltime+"天-"+refund.freetime+"天之间)"+
                "       </div>" +
                "       <div class='refund-line'>" +
                "           <span>不允许退票期限：</span>" +
                "               <ul>"+refund.fulltime+"天内"+"</ul>" +
                "       </div>" +
                "       <div class='refund-line'>" +
                "           <span>参与电影：</span>" +
                "               <ul>"+movieDomStr+"</ul>" +
                "       </div>" +
                "    </div>" +
                /*
                "    <div class='refund-coupon primary-bg'>" +
                "        <span class='title'>退票策略包含退票时间、电影范围和退票范围 咱也不知道还有啥需求</span>" +
                "    </div>" +
                */
                "</div>";
        });
        $(".content-refund").append(refundsDomStr);
    }

//这个方法是表单里面多选电影的getmovie方法
    function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                movieList = res.content;
                $('#refund-movie-input').append("<option value="+ -1 +">所有电影</option>");
                $('#c-refund-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#refund-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                    $('#c-refund-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }

//refund-form-btn是html中的确认按钮
    $("#refund-form-btn").click(function () {
       initstate=false;
       var name=$("#refund-name-input").val();
       $("#c-refund-free-input").value=$("#refund-free-input").val();
       $("#c-refund-full-input").value=$("#refund-full-input").val();
       $("#c-refund-discount-input").value=$("#refund-discount-input").val();
       var form = {
           freetime: $("#refund-free-input").val(),
           //discounttime: $("#refund-time-input").val(),
           fulltime: $("#refund-full-input").val(),
           discount: $("#refund-discount-input").val(),
           movieList: [...selectedMovieIds],
       };
       document.getElementById("NumError").setAttribute("hidden",true);
       document.getElementById("blankError-one").setAttribute("hidden",true);
       document.getElementById("blankError-two").setAttribute("hidden",true);
       document.getElementById("blankError-three").setAttribute("hidden",true);
       var pd=validaterefund(form);
       if(pd!=0){
            switch(pd){
                case 7:var errortext=document.getElementById("NumError");
                       errortext.removeAttribute("hidden");
                       break;
                case 1:var errortext=document.getElementById("blankError-one");
                       errortext.removeAttribute("hidden");
                       break;
                case 2:var errortext=document.getElementById("blankError-two");
                       errortext.removeAttribute("hidden");
                       break;
                case 3:var errortext=document.getElementById("blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 123:var errortext=document.getElementById("blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("blankError-two");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 12:var errortext=document.getElementById("blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("blankError-two");
                       errortext.removeAttribute("hidden");
                       break;
                case 13:
                       var errortext=document.getElementById("blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 23:
                       var errortext=document.getElementById("blankError-two");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("blankError-three");
                       errortext.removeAttribute("hidden");
                       break;

            }
        }
       else{
        postRequest(
            '/refund/publish',
            form,
            function (res) {
                if(res.success){
                    alert("publish success");
                    getRefunds();
                    $("#refundModal").modal('hide');
                    $("#releaseBtn").attr("disabled", true);
                    $("#changeBtn").attr("disabled", false);

                    //$("#changeBtn").modal('show');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );}
    });


//修改电影的确认按钮
    $("#c-refund-form-btn").click(function () {
       var name=$("#c-refund-name-input").val();
       var form = {
           freetime: $("#c-refund-free-input").val(),
           //discounttime: $("#c-refund-time-input").val(),
           fulltime: $("#c-refund-full-input").val(),
           discount: $("#c-refund-discount-input").val(),
           movieList: [...c_selectedMovieIds],
       };
       document.getElementById("c-NumError").setAttribute("hidden",true);
       document.getElementById("c-blankError-one").setAttribute("hidden",true);
       document.getElementById("c-blankError-two").setAttribute("hidden",true);
       document.getElementById("c-blankError-three").setAttribute("hidden",true);
       var pd=validaterefund(form);
       if(pd!=0){
            switch(pd){
                case 7:var errortext=document.getElementById("c-NumError");
                       errortext.removeAttribute("hidden");
                       break;
                case 1:var errortext=document.getElementById("c-blankError-one");
                       errortext.removeAttribute("hidden");
                       break;
                case 2:var errortext=document.getElementById("c-blankError-two");
                       errortext.removeAttribute("hidden");
                       break;
                case 3:var errortext=document.getElementById("c-blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 123:var errortext=document.getElementById("c-blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("c-blankError-two");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("c-blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 12:var errortext=document.getElementById("c-blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("c-blankError-two");
                       errortext.removeAttribute("hidden");
                       break;
                case 13:
                       var errortext=document.getElementById("c-blankError-one");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("c-blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
                case 23:
                       var errortext=document.getElementById("c-blankError-two");
                       errortext.removeAttribute("hidden");
                       var errortext=document.getElementById("c-blankError-three");
                       errortext.removeAttribute("hidden");
                       break;
            }
       }
       else{
        postRequest(
            '/refund/publish',
            form,
            function (res) {
                if(res.success){
                    alert("change success");
                    getRefunds();
                    $("#c-refundModal").modal('hide');
                    //$("#releaseBtn").attr("disabled", true);
                    //$("#changeBtn").attr("disabled", false);
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );}
    });

//选择电影的按钮
        get_ac_movie();
    $('#refund-movie-input').change(function () {
        var movieId = $('#refund-movie-input').val();
        var movieName = $('#refund-movie-input').children('option:selected').text();

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


//点击修改按钮时将选择电影栏清空
     $("#changeBtn").click(function () {
        init_select_movie=true;
        c_selectedMovieIds.clear();
        c_selectedMovieNames.clear();
        $('#c-selected-movies').empty();
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
    function validaterefund(form) {
            var timeone= form.freetime;
            var timetwo= form.fulltime;
            var discount= form.discount;
            if(timeone.length==0||timetwo.length==0||discount.length==0){
                if(timeone.length==0&&timetwo.length==0&&discount.length==0){return 123;}
                if(timeone.length==0&&timetwo.length==0){return 13;}
                if(timeone.length==0&&discount.length==0){return 12;}
                if(timetwo.length==0&&discount.length==0){return 23;}
                if(timeone.length==0){return 1;}
                if(timetwo.length==0){return 3;}
                if(discount.length==0){return 2;}
            }
            else{
                if(isNaN(timeone)||isNaN(timetwo)||isNaN(discount)||eval(timeone)<eval(timetwo)){
                    return 7;
                }
                else{
                    if(eval(timeone)<0||eval(timetwo<0)||eval(discount<0)||eval(discount>100)){
                                            return 7;
                                           }
                    else{return 0;}
                }

            }
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

//c-movie的备份
//选择电影的按钮
        c_get_ac_movie();
    $('#c-refund-movie-input').change(function () {
        var movieId = $('#c-refund-movie-input').val();
        var movieName = $('#c-refund-movie-input').children('option:selected').text();

        if(c_init_select_movie){
            c_selectedMovieIds.clear();
            c_selectedMovieNames.clear();
            c_init_select_movie=false;
        }

        if(movieId==-1){
            c_selectedMovieIds.clear();
            c_selectedMovieNames.clear();
            for(var i=0;i<c_movieList.length;i++){
                var movieVO=c_movieList[i];
                c_selectedMovieIds.add(movieVO.id);
                c_selectedMovieNames.add(movieVO.name);
            }
        } else {
            c_selectedMovieIds.add(movieId);
            c_selectedMovieNames.add(movieName);

        }
        c_renderSelectedMovies();
    });

    function c_get_ac_movie(){
        getRequest(
                    '/movie/all/exclude/off',
                    function (res) {
                        c_movieList = res.content;
                        for(var i=0;i<c_movieList.length;i++){
                            var movieVO=c_movieList[i];
                            c_selectedMovieIds.add(movieVO.id);
                            c_selectedMovieNames.add(movieVO.name);
                        }
                        c_renderSelectedMovies();
                    },
                    function (error) {
                        alert(error);
                    }
             );
        }

    //渲染选择的参加活动的电影
    function c_renderSelectedMovies() {
        $('#c-selected-movies').empty();
        var c_moviesDomStr = "";
        c_selectedMovieNames.forEach(function (movieName) {
            c_moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#c-selected-movies').append(c_moviesDomStr);
    }

});