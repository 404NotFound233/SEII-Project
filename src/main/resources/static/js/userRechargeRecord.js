var bodyContent = "";
$(document).ready(function () {
    getRecordList();
    function getRecordList() {
        getRequest(
            '/vip/get/record/' + sessionStorage.getItem('id'),//sessionStorage.getItem('id')
            function (res) {
                renderRecordList(res.content);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }


    function renderRecordList(list) {
          for(var i = 0; i < list.length; i++) {
            var single_recharge=list[i].single_recharge;
            var date=list[i].joinDate.slice(0,10)+" "+list[i].joinDate.slice(11,19);
			var detail = list[i];
            bodyContent += "<tr><td>" + single_recharge + "</td>" +
					 "<td>" + date + "</td>"+
					 "<td><button type='button' class='btn btn-primary' id='detail-btn' data-detail="+JSON.stringify(detail)+" data-backdrop='static' data-toggle='modal' data-target='#detailModal'>详情</button></td></tr>";                
         }
         $('#ticket-body').html(bodyContent);
    }
	
	$(document).on('click','#detail-btn',function (e) {
            var detail = JSON.parse(e.target.dataset.detail);
			var before_Balance=detail.before_Balance.toFixed(2);
			$("#before_balance").text(before_Balance);
			var single_recharge=detail.single_recharge.toFixed(2);
			$("#single_recharge").text(single_recharge);
			var after_Balance=detail.after_Balance.toFixed(2);
			$("#after_balance").text(after_Balance);
			var date=detail.joinDate.slice(0,10)+" "+detail.joinDate.slice(11,16);
			$("#join_time").text(date);
            $('#detailModal').modal('show');
            //$('#hallEditModal')[0].dataset.hallId = hall.id;
        });
});