var selectedSeats = []
var scheduleId;
var userId;
var schedule00;
var movieID;
var order = {ticketId: [], couponId: 0};
var coupons_get = [];
var coupons = [];
var activities=[];
var isVIP = false;
var useVIP = true;

//var ticket_vo_list;
var actualTotal;
var total;
var balance=1000000;//此处表示银行卡或会员卡的余额，初始化为银行卡余额，应该是无穷大嘛？

//var ticketprice;
var orderInfo="";

$(document).ready(function () {
    scheduleId = parseInt(window.location.href.split('?')[1].split('&')[1].split('=')[1]);
    userId=sessionStorage.getItem('id');
    //userId=parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);

    getInfo();

    function getInfo() {
    //获得已经被占的座位以及movieId
        getRequest(
            '/ticket/get/occupiedSeats?scheduleId=' + scheduleId,
            function (res) {
                if (res.success) {
                    renderSchedule(res.content.scheduleItem, res.content.seats);
                    schedule00=res.content.scheduleItem;
                    movieID=schedule00.movieId;
                }
                //根据movieId获得活动，获得用户本次可以获得但是不能使用的优惠券，主要用于添加到数据库之中
                //可以在付款成功的里面写一个postrequest将优惠券添加到数据库中和用户对应
                getRequest(
                          '/activity/get/'+movieID,
                          function (res) {
                                activities = res.content;
                                for(var i=0;i<activities.length;i++){
                                      var coupon=activities[i].coupon;
                                      coupons_get.push(coupon);
                                }
                          },
                          function (error) {
                                alert(error);
                          }
                    );
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }
});

function renderSchedule(schedule, seats) {
    $('#schedule-hall-name').text(schedule.hallName);
    $('#order-schedule-hall-name').text(schedule.hallName);
    $('#schedule-fare').text(schedule.fare.toFixed(2));
    $('#order-schedule-fare').text(schedule.fare.toFixed(2));
    $('#schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    $('#order-schedule-time').text(schedule.startTime.substring(5, 7) + "月" + schedule.startTime.substring(8, 10) + "日 " + schedule.startTime.substring(11, 16) + "场");
    //ticketprice=schedule.fare.toFixed(2);

    var hallDomStr = "";
    var seat = "";
    for (var i = 0; i < seats.length; i++) {
        var temp = ""
        for (var j = 0; j < seats[i].length; j++) {
            var id = "seat" + i + j ;

            if (seats[i][j] == 0) {
                // 未选
                temp += "<button class='cinema-hall-seat-choose' id='" + id + "' onclick='seatClick(\"" + id + "\"," + i + "," + j + ")'></button>";
            } else {
                // 已选中
                temp += "<button class='cinema-hall-seat-lock'></button>";
            }
        }
        seat += "<div>" + temp + "</div>";
    }
    var hallDom =
        "<div class='cinema-hall'>" +
        "<div>" +
        "<span class='cinema-hall-name'>" + schedule.hallName + "</span>" +
        "<span class='cinema-hall-size'>" + seats.length + '*' + seats[0].length + "</span>" +
        "</div>" +
        "<div class='cinema-seat'>" + seat +
        "</div>" +
        "</div>";
    hallDomStr += hallDom;

    $('#hall-card').html(hallDomStr);
}

function seatClick(id, i, j) {
    let seat = $('#' + id);
    if (seat.hasClass("cinema-hall-seat-choose")) {
        seat.removeClass("cinema-hall-seat-choose");
        seat.addClass("cinema-hall-seat");

        selectedSeats[selectedSeats.length] = [i, j]
    } else {
        seat.removeClass("cinema-hall-seat");
        seat.addClass("cinema-hall-seat-choose");

        selectedSeats = selectedSeats.filter(function (value) {
            return value[0] != i || value[1] != j;
        })
    }

    selectedSeats.sort(function (x, y) {
        var res = x[0] - y[0];
        return res === 0 ? x[1] - y[1] : res;
    });

    let seatDetailStr = "";
    if (selectedSeats.length == 0) {
        seatDetailStr += "还未选择座位"
        $('#order-confirm-btn').attr("disabled", "disabled")
    } else {
        for (let seatLoc of selectedSeats) {
            seatDetailStr += "<span>" + (seatLoc[0] + 1) + "排" + (seatLoc[1] + 1) + "座</span>";
        }
        $('#order-confirm-btn').removeAttr("disabled");
    }
    $('#seat-detail').html(seatDetailStr);
}

function orderConfirmClick() {
    $('#seat-state').css("display", "none");
    $('#order-state').css("display", "");
    var seatFormlist = [];
	for (let seatLoc of selectedSeats){
		//seat是后台Seat的json对象
		var seat={"columnIndex":(seatLoc[1] ),"rowIndex":(seatLoc[0])};
		seatFormlist.push(seat);
	}
	var TicketForm={
    		"userId":userId,
    		"scheduleId":scheduleId,
    		"seats":seatFormlist
    };
    // TODO:这里是假数据，需要连接后端获取真数据，数据格式可以自行修改，但如果改了格式，别忘了修改renderOrder方法
    var orderInfo={ }
    /*
        "ticketVOList":ticket_vo_list,
        "total": selectedSeats.length*schedule00.fare.toFixed(2),
        "coupons":coupons,
        "activities":activities*/

/*
    var orderInfo = {
            "ticketVOList":ticket_vo_list,
            "total": selectedSeats.length*schedule00.fare.toFixed(2),
            "coupons":coupons,
            "activities":activities
    };

    //renderOrder(orderInfo);


	$('#order-total').text(ticketprice*selectedSeats);
	var SeatFormlist = [];
	for (let seatLoc of selectedSeats){
		//seat是后台Seat的json对象
			var seat={"columnIndex":(seatLoc[1] ),"rowIndex":(seatLoc[0])};
			SeatFormlist.push(seat);}
	var userId=parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
	var TicketForm={
		"userId":userId,
		"scheduleId":scheduleId,
		"seats":SeatFormlist
	};
	*/
    getRequest(
        '/vip/' + sessionStorage.getItem('id') + '/get',
        function (res) {
            isVIP = res.success;
            useVIP = res.success;
            if(res.success){
                balance=res.content.balance.toFixed(2);
            }
            if (isVIP) {
                $('#member-balance').html("<div><b>会员卡余额：</b>" + res.content.balance.toFixed(2) + "元</div>");
            } else {
                $("#member-pay").css("display", "none");
                $("#nonmember-pay").addClass("active");

                $("#modal-body-member").css("display", "none");
                $("#modal-body-nonmember").css("display", "");
            }
        },
        function (error) {
            alert(error);
        })


        //根据TicketForm获得一个TicketWithCouponVO，从而得到orderInfo
        	//此处的优惠券是用户现在拥有的，本次可以使用的
        	postRequest(
            		"/ticket/lockSeat",
            		TicketForm,
            		function(res){
            		    if(res.success){
            				orderInfo={
            				        "ticketVOList":res.content.ticketVOList,
                                    "total": res.content.total,
                                    "coupons":res.content.coupons,
                                    "activities":res.content.activities
            				};
            			    renderOrder(orderInfo);
            			}
            		    else{$('#order-total').text("锁座失败");}
            		}
            	);
}

function switchPay(type) {
    useVIP = (type == 0);
    if (type == 0) {
        $("#member-pay").addClass("active");
        $("#nonmember-pay").removeClass("active");

        $("#modal-body-member").css("display", "");
        $("#modal-body-nonmember").css("display", "none");
    } else {
        $("#member-pay").removeClass("active");
        $("#nonmember-pay").addClass("active");

        $("#modal-body-member").css("display", "none");
        $("#modal-body-nonmember").css("display", "");
    }
}

function renderOrder(orderInfo) {
    var ticketStr = "<div>" + selectedSeats.length + "张</div>";
    for (let ticketInfo of orderInfo.ticketVOList) {
        ticketStr += "<div>" + (ticketInfo.rowIndex + 1) + "排" + (ticketInfo.columnIndex + 1) + "座</div>";
        order.ticketId.push(ticketInfo.id);
    }
    $('#order-tickets').html(ticketStr);

    total = orderInfo.total.toFixed(2);
    $('#order-total').text(total);
    $('#order-footer-total').text("总金额： ¥" + total);


    var couponTicketStr = "";
    actualTotal=total;
    if (orderInfo.coupons.length == 0) {
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
    } else {
        coupons = orderInfo.coupons;
        for (let coupon of coupons) {
            couponTicketStr += "<option>满" + coupon.targetAmount + "减" + coupon.discountAmount + "</option>"
        }
        $('#order-coupons').html(couponTicketStr);
        changeCoupon(0);//此处的0是什么意思？此行是不是应该删除？因为html里面已经发出了此方法的请求，还是默认第一个？
    }
}


function changeCoupon(couponIndex) {
    if(parseFloat(total)>parseFloat(coupons[couponIndex].targetAmount)){
        order.couponId = coupons[couponIndex].id;
        $('#order-discount').text("优惠金额： ¥" + coupons[couponIndex].discountAmount.toFixed(2));
        actualTotal = (parseFloat($('#order-total').text()) - parseFloat(coupons[couponIndex].discountAmount)).toFixed(2);
        $('#order-actual-total').text(" ¥" + actualTotal);
        $('#pay-amount').html("<div><b>金额：</b>" + actualTotal + "元</div>");
    }
    else{
        $('#order-discount').text("优惠金额：无");
        $('#order-actual-total').text(" ¥" + total);
        $('#pay-amount').html("<div><b>金额：</b>" + total + "元</div>");
    }
}

function payConfirmClick() {
    if (useVIP) {
        if(parseFloat(balance)<parseFloat(actualTotal)){
            alert("会员卡余额不足");
        }
        else{
            postPayRequest("memberPay");
        }
    } else {
        if (validateForm()) {
            if ($('#userBuy-cardNum').val() === "123123123" && $('#userBuy-cardPwd').val() === "123123") {
                postPayRequest("nonmemberPay");
            } else {
                alert("银行卡号或密码错误");
            }
        }
    }
}

// TODO:填空
function postPayRequest(str) {
    $('#order-state').css("display", "none");
    $('#success-state').css("display", "");
    $('#buyModal').modal('hide')
    if(str=="memberPay"){
        vip_buy();
        vip_pay();


    }
    else{
        user_buy();

    }
    for(let couponToGet of coupons_get){
           give_coupons(couponToGet.id,userId);
    }
}

function vip_buy(){
    postRequest(
            '/ticket/vip/buy/'+order.ticketId+'/'+order.couponId+'/'+actualTotal,
            order.ticketId,
            order.couponId,
            actualTotal,
            function (res) {
            //此处经过测试可以运行到
            },
            function (error) {
                 alert(error);
            }
        );
}

function vip_pay(){
    postRequest(
            '/vip/pay/'+sessionStorage.getItem('id')+'/'+parseFloat(balance-actualTotal),
            sessionStorage.getItem('id'),
            parseFloat(balance-actualTotal),
            function(res){
             alert("ok!!!!!");//此处未能运行到
            },
            function(error){
                alert(error);
            }
        );
}

function user_buy(){
      postRequest(
            '/ticket/buy/'+order.ticketId+'/'+order.couponId+'/'+actualTotal,
            order.ticketId,
            order.couponId,
            actualTotal,
            function (res) {
            },
            function (error) {
                 alert(error);
            }
        );
}

function give_coupons(couponId,user_Id){
    postRequest(
        '/coupon/issue/'+couponId+'/'+user_Id,
        couponId,
        userId,
        function(res){
        },
        function(error){
            alert(error);
        }
    )
}

function validateForm() {
    var isValidate = true;
    if (!$('#userBuy-cardNum').val()) {
        isValidate = false;
        $('#userBuy-cardNum').parent('.form-group').addClass('has-error');
        $('#userBuy-cardNum-error').css("visibility", "visible");
    }
    if (!$('#userBuy-cardPwd').val()) {
        isValidate = false;
        $('#userBuy-cardPwd').parent('.form-group').addClass('has-error');
        $('#userBuy-cardPwd-error').css("visibility", "visible");
    }
    return isValidate;
}