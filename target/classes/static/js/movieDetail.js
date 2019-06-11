$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
       getRequest(
           '/movie/' + movieId + '/like/date',
           function(res){
               var data = res.content,
                    dateArray = [],
                    numberArray = [];
               data.forEach(function (item) {
                   dateArray.push(item.likeTime);
                   numberArray.push(item.likeNum);
               });

               var myChart = echarts.init($("#like-date-chart")[0]);

               // 指定图表的配置项和数据
               var option = {
                   title: {
                       text: '想看人数变化表'
                   },
                   xAxis: {
                       type: 'category',
                       data: dateArray
                   },
                   yAxis: {
                       type: 'value'
                   },
                   series: [{
                       data: numberArray,
                       type: 'line'
                   }]
               };

               // 使用刚指定的配置项和数据显示图表。
               myChart.setOption(option);
           },
           function (error) {
               alert(error);
           }
       );
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }
	
    
	
    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
             url,
            null,
            function (res) {
                 isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });



    $("#modify-btn").click(function(){
		getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                name: $('#movie-name-input').val(res.content.name);
                startDate: $('#movie-date-input').val(res.content.startDate.slice(0,10));
                posterUrl: $('#movie-img-input').val(res.content.posterUrl);
                description: $('#movie-description-input').val(res.content.description);
                type: $('#movie-type-input').val(res.content.type);
                length: $('#movie-length-input').val(res.content.length);
                country: $('#movie-country-input').val(res.content.country);
                starring: $('#movie-star-input').val(res.content.starring);
                director: $('#movie-director-input').val(res.content.director);
                screenWriter: $('#movie-writer-input').val(res.content.screenWriter);
                language: $('#movie-language-input').val(res.content.language);

            },
            function (error) {
                alert(error);
            }
        );


     });

    // admin界面才有
    $("#movie-conf-btn").click(function () {
       //alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
       //要在html的实现中把原数据设成初始数据（修改打开来不为空），从数据库里调一个出来，然后 下架的部分可以通过update实现（status改为1？还是0？）
        //修改排片！！

		var Name = $('#movie-name-input').val();
		var Startdate = $('#movie-date-input').val();
        var Img = $('#movie-img-input').val();
	    var Description = $('#movie-description-input').val();
	    var Type = $('#movie-type-input').val();
	    var Length = $('#movie-length-input').val();
	    var Country = $('#movie-country-input').val();
	    var Star = $('#movie-star-input').val();
	    var Director = $('#movie-director-input').val();
	    var Writer = $('#movie-writer-input').val();
	    var Language = $('#movie-language-input').val();
        var formData = {
           id:Number(movieId),
           name: Name,
           startDate: Startdate,
           posterUrl: Img,
           description: Description,
           type: Type,
           length: Length,
           country: Country,
           starring: Star,
           director: Director,
           screenWriter: Writer,
           language: Language

       };
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
             '/movie/update',
            formData,
            function (res) {
                if(res.message=="更新失败"){
                    alert("更新失败，有电影后续仍有排片或已有观众购票且未使用！");
                    }
                else{
                    alert("修改成功");
                }
                $("#movieModal").modal('hide');
                getMovieList();
                window.location.reload();
				
            },
             function (error) {
                alert("click fail");
            });
        
    });


    //验证是否有效，无效则变成has-error的样式
    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
			$('#movie-name-error').css("visibility", "visible");
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
			$('#movie-img-error').css("visibility", "visible");
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
			$('#movie-date-error').css("visibility", "visible");
        }
        return isValidate;
    }


    function getMovieList() {
        getRequest(
            '/movie/all',
            function (res) {
                 renderMovieList(res.content);
                //显示修改后的电影信息
            },
            function (error) {
                alert('get error');
            }
        );
    }

    function renderMovieList(list) {
        $('.movie-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movie.description = movie.description || '';
            movieDomStr +=
                "<li class='movie-item card'>" +
                "<img class='movie-img' src='" + (movie.posterUrl || "../images/defaultAvatar.jpg") + "'/>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + movie.name + "</span>" +
                "<span class='label "+(!movie.status ? 'primary-bg' : 'error-bg')+"'>" + (movie.status ? '已下架' : (new Date(movie.startDate)>=new Date()?'未上映':'热映中')) + "</span>" +
                "<span class='movie-want'><i class='icon-heart error-text'></i>" + (movie.likeCount || 0) + "人想看</span>" +
                "</div>" +
                "<div class='movie-description dark-text'><span>" + movie.description + "</span></div>" +
                "<div>类型：" + movie.type + "</div>" +
                "<div style='display: flex'><span>导演：" + movie.director + "</span><span style='margin-left: 30px;'>主演：" + movie.starring + "</span>" +
                "<div class='movie-operation'><a href='/admin/movieDetail?id="+ movie.id +"'>详情</a></div></div>" +
                "</div>"+
                "</li>";
        });
        $('.movie-on-list').append(movieDomStr);
    }

    $("#delete-btn").click(function () {
        //alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
        /*下架：
        确认提示框（admin）
        在list页面~热映中改为已下架（both）-->更新movie status
        ?? 可能改变status之后 自动会没有的 在排片的选择列表里去掉！（不可被排片，用户就不可购票）（admin）
        在详情页面~下架按钮改为已下架，不可点击了(先不管他吧?♪(^∇^*))
        */
        
		var r=confirm("确认要下架吗？")
        if (r) {
			postRequest(
				'/movie/off/batch',
				{movieIdList:[Number(movieId)]},
				function(res) {
					alert("success");
				},
				function(error){
					alert("post 失败");
				});
		}	
    });
 

});