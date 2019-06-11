//愿我能de出优美的bug
//js我是你爸爸
var hallName="hallName";
var movieName="movieName";
var startTime=null;
var endTime=null;
var bodyContent = "";
//这里的movieList是一个ticketVO的数组
var movieList=[];
var ticketIdclicked=0;


//为了继续支付
var isVIP = false;
var useVIP = true;
var balance=1000000;
var fare;
var vip_discount;

function getVIPInfo(){
                getRequest(
                     '/vip/getVIPInfo',
                     function(res){
                         vip_discount=res.content.discount;
                     },
                     function (error) {
                         alert(error);
                     }
                );
            }
        function getMovieList() {
            bodyContent = "";
            getRequest(
                '/ticket/get/' + sessionStorage.getItem('id'),//sessionStorage.getItem('id')
                function (res) {
                    //console.log(res.content.length);
                    renderTicketList(res.content);
                },
                function (error) {
                    alert(error);
                });
        }

// TODO:填空
         //此处还需继续修改
         //list是<List>ticketVO
         function renderTicketList(list) {
              movieList=list;
              if(list.length==0){
                  $('#ticket-body').html(bodyContent);
              }
              for (var i = 0; i < list.length; i++) {
                   var seatName=(list[i].rowIndex+1)+"排"+(list[i].columnIndex+1)+"座";
                   var scheduleId=list[i].scheduleId;
                   var state=list[i].state;
                   //console.log("do"+i+"times");
                   compl_info(seatName,scheduleId,state,i,list);
              }
         }

         function compl_info(seatName,scheduleId,state,i,list){
         getRequest(
               "/schedule/"+list[i].scheduleId,
               function(res){
                     hallName=res.content.hallName;
                     movieName=res.content.movieName;
                     startTime=res.content.startTime.slice(0,10)+" "+res.content.startTime.slice(11,19);
                     endTime=res.content.endTime.slice(0,10)+" "+res.content.endTime.slice(11,19);
                     if(state=="已完成"){
                     bodyContent += "<tr><td>" + movieName + "</td>" +
                                                  "<td>" + hallName + "</td>" +
                                                  "<td>" + seatName + "</td>" +
                                                  "<td>" + startTime + "</td>" +
                                                  "<td>" + endTime + "</td>" +
                                                  "<td>" + state + "</td>"+
                                                  "<td>"+"<button type='button' id="+list[i].id+" class='btn btn-primary tp' data-backdrop='static' data-toggle='modal' data-target='#tpModal'>退票</button>"+"</td>"+
                                                  "<td>"+"<button type='button' id="+list[i].id*(-1)+" class='btn btn-primary cp'>出票</button>"+"</td>"+
                                                  "</tr>";
                     }
                     else{  if(state=="已失效"||state=="已出票"){
                           bodyContent += "<tr><td>" + movieName + "</td>" +
                                                  "<td>" + hallName + "</td>" +
                                                  "<td>" + seatName + "</td>" +
                                                  "<td>" + startTime + "</td>" +
                                                  "<td>" + endTime + "</td>" +
                                                  "<td>" + state + "</td>"+
                                                  "<td>"+"<button type='button' disabled='disabled' id="+list[i].id+" class='btn btn-primary'>退票</button>"+"</td>"+
                                                  "<td>"+"<button type='button' disabled='disabled' id="+list[i].id*(-1)+" class='btn btn-primary cp'>出票</button>"+"</td>"+
                                                  "</tr>";
                                             }
                                           else{
                           bodyContent += "<tr><td>" + movieName + "</td>" +
                                                  "<td>" + hallName + "</td>" +
                                                  "<td>" + seatName + "</td>" +
                                                  "<td>" + startTime + "</td>" +
                                                  "<td>" + endTime + "</td>" +
                                                  "<td>" + state + "</td>"+
                                                  "<td>"+"<button type='button' id="+list[i].id+" class='btn btn-primary cancel'>取消</button>"+"</td>"+
                                                  "<td>"+"<button type='button' id="+list[i].id*(-1)+" class='btn btn-primary goback' data-backdrop='static' data-toggle='modal' data-target='#gobackModal'>支付</button>"+"</td>"+
                                                  "</tr>";
                                           }
                                   }
                               $('#ticket-body').html(bodyContent);
                             },
                             function(error){
                             alert(error);
                             }
              );
         }

$(document).ready(function () {
    getMovieList();
    getVIPInfo();


        $(document).on('click', '.tp',function(e){
                        var list=movieList;
                        var movieId=e.target.id;
                        for (var i = 0; i < list.length; i++) {
                            var ticketVO=list[i];
                            if(ticketVO.id==movieId){
                                //alert("I found it"+movieId);
                            ticketIdclicked=list[i].id;
                            var sn=(list[i].rowIndex+1)+"排"+(list[i].columnIndex+1)+"座";
                            document.getElementById("seatName").innerHTML=sn;
                            document.getElementById("actualTotal").innerHTML=list[i].actualTotal;

                            if(list[i].location==0){
                                document.getElementById("location").innerHTML="会员卡";}
                            else{
                                document.getElementById("location").innerHTML="银行卡";}
                        getRequest(
                                "/schedule/"+list[i].scheduleId,
                                function(res){
                                      //var sn=(list[i].rowIndex+1)+"排"+(list[i].columnIndex+1)+"座";
                                      hallName=res.content.hallName;
                                      movieName=res.content.movieName;
                                      startTime=res.content.startTime.slice(0,10)+" "+res.content.startTime.slice(11,19);
                                      endTime=res.content.endTime.slice(0,10)+" "+res.content.endTime.slice(11,19);
                                      document.getElementById("movieName").innerHTML=movieName;
                                      document.getElementById("hallName").innerHTML=hallName;
                                      //document.getElementById("seatName").innerHTML=sn;
                                      document.getElementById("startTime").innerHTML=startTime;
                                      document.getElementById("finishTime").innerHTML=endTime;
                                },
                                function(error){
                                alert(error);
                                }
                            );
                            }
                            }
                                 }
                        );
        $(document).on('click', '.cp',function(e){
                    var list=movieList;
                    var movieId=e.target.id*(-1);
                     for (var i = 0; i < list.length; i++) {
                        var ticketVO=list[i];
                        if(ticketVO.id==movieId){
                            //alert("I found it"+movieId);
                            ticketIdclicked=list[i].id;
                            getRequest(
                                   "/ticket/change?ticketId="+ticketIdclicked,
                                   function(res){
                                          alert("出票成功");
                                          getMovieList();
                                          },
                                   function(error){
                                          alert(error);
                                                        }
                            );
                        }
                     }
          }
        );

            $(document).on('click', '.cancel',function(e){
                        var list=movieList;
                        var movieId=e.target.id;
                         for (var i = 0; i < list.length; i++) {
                            var ticketVO=list[i];

                            if(ticketVO.id==movieId){

                                //alert("I found it"+movieId);
                                ticketIdclicked=list[i].id;
                                postRequest(
                                       "/ticket/delete/"+ticketIdclicked,
                                       ticketIdclicked,
                                       function(res){
                                              alert("取消成功");
                                              getMovieList();
                                              },
                                       function(error){
                                              alert(error);
                                                            }
                                );
                            }
                         }
              }
            );

            //为了继续支付
            $(document).on('click', '.goback',function(e){
                  var list=movieList;
                  var movieId=e.target.id*(-1);
                  var scheduleId;
                  for (var i = 0; i < list.length; i++) {
                       var ticketVO=list[i];
                       if(ticketVO.id==movieId){

                            //alert("I found it"+movieId);
                            ticketIdclicked=list[i].id;
                            scheduleId=ticketVO.scheduleId;
                           getRequest(
                                '/schedule/'+scheduleId,
                                function(res){
                                    fare=res.content.fare;
                                    $('#pay-amount').html("<div><b>金额：</b>" + fare + "元</div>");
                                    $('#vip-pay-amount').html("<div><b>金额：</b>" + parseFloat(fare*(vip_discount/10)).toFixed(2) + "元</div>");
                                },
                                function(error){
                                    alert(error);
                                }
                           )

                           getRequest(
                                   '/vip/' + sessionStorage.getItem('id') + '/get',
                                   function (res) {
                                       isVIP = res.success;
                                       useVIP = res.success;
                                       //如果有会员卡，则将余额改为表示会员卡的余额
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
                                   }
                           )
                       }
                  }
            });


          $("#tp-form-btn").click(function () {
             //console.log(ticketIdclicked);
             var list=[];
             getRequest(
                 		'/ticket/cancel?ticketId=' + ticketIdclicked,
                 		function(res){
                 		    if(res.success){
                                 $("#tpModal").modal('hide');
                                 list=res.content;
                 				alert("退票成功\n"+"退票前余额："+list[0]+"\n"+"退票后余额："+list[1]+"\n"+"买票时付款："+list[2]+"\n"+"退款："+list[3]);
                 				getMovieList();
                 				if(list[0]==-1){
                 				    postRequest(
                                          '/ticket/normalRecord/'+sessionStorage.getItem('id')+'/'+list[2]+'/'+4,
                                          sessionStorage.getItem('id'),
                                          list[2],
                                          4,
                                          function(res){
                                          },
                                          function(error){
                                              alert(error);
                                          }
                                     )
                 				}
                 				else{
                 				    postRequest(
                                         '/ticket/VIPRecord/'+sessionStorage.getItem('id')+'/'+list[2]+'/'+list[0]+'/'+3,
                                         sessionStorage.getItem('id'),
                                         list[2],
                                         list[0],
                                         3,
                                         function(res){
                                         },
                                         function(error){
                                             alert(error);
                                         }
                                     )
                 				}
                 			}
                 		    else{alert(res.message);}
                 		}
                 	);
             });
});

    //为了继续支付
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

    function payConfirmClick() {
        if (useVIP) {
            var disc_actual=parseFloat(fare*(vip_discount/10)).toFixed(2);
            if(parseFloat(balance)<parseFloat(disc_actual)){
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

    function postPayRequest(str) {
        alert("支付成功！");
        $('#gobackModal').modal('hide')
        if(str=="memberPay"){
            vip_buy();
            vip_pay();
        }
        else{
            user_buy();
        }
        window.location.reload();
    }

    function vip_buy(){
        postRequest(
                '/ticket/vip/buy/'+ticketIdclicked+'/'+0+'/'+parseFloat(fare*(vip_discount/10)).toFixed(2),
                ticketIdclicked,
                0,
                parseFloat(fare*(vip_discount/10)).toFixed(2),
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
                '/vip/pay/'+sessionStorage.getItem('id')+'/'+parseFloat(balance-parseFloat(fare*(vip_discount/10)).toFixed(2)),
                sessionStorage.getItem('id'),
                parseFloat(balance-parseFloat(fare*(vip_discount/10)).toFixed(2)),
                function(res){
                 alert("ok!!!!!");//此处未能运行到
                },
                function(error){
                    alert(error);
                }
        );
        postRequest(
            '/ticket/VIPRecord/'+sessionStorage.getItem('id')+'/'+parseFloat(fare*(vip_discount/10)).toFixed(2)+'/'+balance+'/'+2,
            sessionStorage.getItem('id'),
            parseFloat(fare*(vip_discount/10)).toFixed(2),
            balance,
            2,
            function(res){
            },
            function(error){
                alert(error);
            }
        )
    }

    function user_buy(){
          postRequest(
                '/ticket/buy/'+ticketIdclicked+'/'+0+'/'+fare,
                ticketIdclicked,
                0,
                fare,
                function (res) {
                },
                function (error) {
                     alert(error);
                }
            );
          postRequest(
                      '/ticket/normalRecord/'+sessionStorage.getItem('id')+'/'+fare+'/'+3,
                      sessionStorage.getItem('id'),
                      fare,
                      3,
                      function(res){
                      },
                      function(error){
                          alert(error);
                      }
          )
    }













