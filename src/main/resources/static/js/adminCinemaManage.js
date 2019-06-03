$(document).ready(function() {

    var canSeeDate = 0;
	var HallId;

    getCanSeeDayNum();
    getCinemaHalls();
	
    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

	$("#hall-form-btn").click(function() {
		var formData = getHallForm();
		if(!validateHallForm(formData)) {
			return;
		}
		postRequest(
			'/hall/add',
			formData,
			function(res){
				getCinemaHalls();
				$("#hallModal").modal('hide');
			},
			function(error) {
				alert(error);
			});
	});
	
	$("#hall-edit-remove-btn").click(function() {
		var r=confirm("确认要删除该影厅信息吗")
        if (r) {
            deleteRequest(
                '/hall/delete/batch',
				{hallIdList:[Number($('#hallEditModal')[0].dataset.hallId)]},
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#hallEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
	});
	
	$("#hall-edit-form-btn").click(function() {
		var form = {
			id:Number($('#hallEditModal')[0].dataset.hallId),
			name:$('#hall-edit-name-input').val(),
			column:$('#hall-edit-column-input').val(),
			row:$('#hall-edit-row-input').val()
		};
		if(!validateHallForm(form)){
			return;
		}
		postRequest(
			'/hall/update',
			form,
			function(res){
				if(res.success){
					getCinemaHalls();
					$("#hallEditModal").modal('hide');
				}else{
					alert(res.message);
				}
			},
			function (error) {
				alert(JSON.stringify(error));
			}
		);	
	});
	
	$(document).on('click','#hall-modify-btn',function (e) {
            var hall = JSON.parse(e.target.dataset.hall);
            $("#hall-edit-name-input").val(hall.name);
            $("#hall-edit-column-input").val(hall.column);
            $("#hall-edit-row-input").val(hall.row);
            $('#hallEditModal').modal('show');
            $('#hallEditModal')[0].dataset.hallId = hall.id;
            console.log(hall);
        });
	
	function getHallForm() {
		return {
			name:$('#hall-name-input').val(),
			column:$('#hall-column-input').val(),
			row:$('#hall-row-input').val()
		};
	}
	
	function validateHallForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#hall-name-input').parent('.form-group').addClass('has-error');
			$('#hall-name-error').css("visibility", "visible");
        }
        if(!data.column) {
            isValidate = false;
            $('#hall-column-input').parent('.form-group').addClass('has-error');
			$('#hall-column-error').css("visibility", "visible");
        }
        if(!data.row) {
            isValidate = false;
            $('#hall-row-input').parent('.form-group').addClass('has-error');
			$('#hall-row-error').css("visibility", "visible");
        }
        return isValidate;
    }
	
	
    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
				"<a style='margin-left: 20px;' class='primary-text' id='hall-modify-btn' data-hall='"+JSON.stringify(hall)+"'>修改</a>"+
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
});