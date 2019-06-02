var hallName="hallName";
var movieName="movieName";
var startTime=null;
var endTime=null;
var bodyContent = "";
$(document).ready(function () {
    getMovieList();
    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),//sessionStorage.getItem('id')
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    //此处还需继续修改
    function renderTicketList(list) {
         for (var i = 0; i < list.length; i++) {
              var seatName=(list[i].rowIndex+1)+"排"+(list[i].columnIndex+1)+"座";
              var scheduleId=list[i].scheduleId;
              var state=list[i].state;
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
                              if(state!="未完成"){
                              bodyContent += "<tr><td>" + movieName + "</td>" +
                                             "<td>" + hallName + "</td>" +
                                             "<td>" + seatName + "</td>" +
                                             "<td>" + startTime + "</td>" +
                                             "<td>" + endTime + "</td>" +
                                             "<td>" + state + "</td></tr>";
                              }

                        if(i==list.length-1){
                          $('#ticket-body').html(bodyContent);
                        }
                        },
                        function(error){
                        alert(error);
                        }
         );
    }
});