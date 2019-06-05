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
$(document).ready(function () {
    getMovieList();
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

    // TODO:填空
    //此处还需继续修改
    //list是<List>ticketVO
    function renderTicketList(list) {
         movieList=list;
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
                                             "<td>"+"<button type='button' disabled='disabled' id="+list.id+" class='btn btn-primary'>退票</button>"+"</td>"+
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
                                             "<td>"+"<button type='button' disabled='disabled' id="+list.id+" class='btn btn-primary'>退票</button>"+"</td>"+
                                             "<td>"+"<button type='button' disabled='disabled' id="+list[i].id*(-1)+" class='btn btn-primary cp'>出票</button>"+"</td>"+
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

     $("#tp-form-btn").click(function () {
        //console.log(ticketIdclicked);
        getRequest(
            		'/ticket/cancel?ticketId=' + ticketIdclicked,
            		function(res){
            		    if(res.success){
                            $("#tpModal").modal('hide');
                            var list=res.content;
            				alert("退票成功\n"+"退票前余额："+list[0]+"\n"+"退票后余额："+list[1]+"\n"+"买票时付款："+list[2]+"\n"+"退款："+list[3]);
            				getMovieList();
            			}
            		    else{alert(res.message);}
            		}
            	);
        });

});