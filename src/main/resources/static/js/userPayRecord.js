var bodyContent = "";
$(document).ready(function () {
    getVIPRecord();
    getNormalRecord();
    function getVIPRecord() {
        getRequest(
            '/ticket/get/VIPRecord/' + sessionStorage.getItem('id'),//sessionStorage.getItem('id')
            function (res) {
                renderVIPRecord(res.content);
            },
            function (error) {
                alert(JSON.stringify(error));
            });
    }

    function getNormalRecord() {
            getRequest(
                '/ticket/get/normalRecord/' + sessionStorage.getItem('id'),//sessionStorage.getItem('id')
                function (res) {
                    renderNormalRecord(res.content);
                },
                function (error) {
                    alert(JSON.stringify(error));
                });
        }


    function renderVIPRecord(list) {
          bodyContent="";
          for(var i = 0; i < list.length; i++) {
               var vamount=list[i].amountString;
               var vjoin_time=list[i].join_time.slice(0,10)+" "+list[i].join_time.slice(11,19);
			   var detail = list[i];
               bodyContent += "<tr><td>" + vamount + "</td>" +
			   "<td>" + vjoin_time + "</td>"+
			   "<td><button type='button' class='btn btn-primary' id='vip-detail-btn' data-detail="+JSON.stringify(detail)+" data-backdrop='static' data-toggle='modal' data-target='#vipDetailModal'>详情</button></td></tr>";
         }
         $('#vip-pay-body').html(bodyContent);
    }

    function renderNormalRecord(list) {
         bodyContent="";
         for(var i = 0; i < list.length; i++) {
               var amount=list[i].amountString;
               var join_time=list[i].join_time.slice(0,10)+" "+list[i].join_time.slice(11,19);
               var detail = list[i];
               bodyContent += "<tr><td>" + amount + "</td>" +
    		   "<td>" + join_time + "</td>"+
    		   "<td><button type='button' class='btn btn-primary' id='normal-detail-btn' data-detail="+JSON.stringify(detail)+" data-backdrop='static' data-toggle='modal' data-target='#normalDetailModal'>详情</button></td></tr>";
         }
         $('#normal-pay-body').html(bodyContent);
    }

	$(document).on('click','#vip-detail-btn',function (e) {
            var detail = JSON.parse(e.target.dataset.detail);
			var vbefore_Balance=detail.before_Balance.toFixed(2);
			$("#vbefore_balance").text(vbefore_Balance);
			var vamount=detail.amountString;
			$("#vamount").text(vamount);
			var vafter_Balance=detail.after_Balance.toFixed(2);
			$("#vafter_balance").text(vafter_Balance);
			var vjoin_time=detail.join_time.slice(0,10)+" "+detail.join_time.slice(11,16);
			$("#vjoin_time").text(vjoin_time);
			var vpay_reason=detail.reasonString;
            $("#vpay_reason").text(vpay_reason);
            $('#vipDetailModal').modal('show');
            //$('#hallEditModal')[0].dataset.hallId = hall.id;
        });

    $(document).on('click','#normal-detail-btn',function (e) {
         var detail = JSON.parse(e.target.dataset.detail);
	     var amount=detail.amountString;
		 $("#amount").text(amount);
		 var join_time=detail.join_time.slice(0,10)+" "+detail.join_time.slice(11,16);
		 $("#join_time").text(join_time);
		 var pay_reason=detail.reasonString;
         $("#pay_reason").text(pay_reason);
         $('#normalDetailModal').modal('show');
         //$('#hallEditModal')[0].dataset.hallId = hall.id;
    });
});