var length;
var first=true;
var vip;
$(document).ready(function() {
    getLength();
    function getLength(){
        getRequest(
            '/vip/length',
            function(res){
                length=parseInt(res.content);
                if(length==0){
                        $('#modify-vip-btn').attr("disabled", "disabled");
                }
                if(length==1){
                     if(first){
                         $('#modify-vip-btn').removeAttr("disabled");
                         first=false;
                     }
                         getVIPInfo();
                         $('#publish-vip-btn').attr("disabled", "disabled");
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    function renderVIP(vip) {
        $(".content-vip").empty();
        var vipDomStr = "";
        vipDomStr+=
                        "<div class='refund-container'>" +
                        "    <div class='refund-card card'>" +
                        "       <div class='refund-line'>" +
                        "           <span class='title'>会员卡策略</span>" +
                        "           <span class='gray-text'>"+"会员策略包含会员卡价格、描述、购票优惠和充值优惠"+"</span>" +
                        "       </div><br>" +

                        "       <div class='refund-line'>" +
                        "           <span>会员卡价格：</span>" +
                        "               <ul id='vip-price'></ul>元" +
                        "       </div><br>" +

                        "       <div class='refund-line'>" +
                        "           <span>会员卡描述：</span>" +
                        "               <ul id='vip-description'></ul>" +
                        "       </div><br>" +
                        "       <div class='refund-line'>" +
                        "           <span>购票优惠：</span>" +
                        "               <ul id='vip-discount'></ul>折" +
                        "       </div><br>" +
                        "       <div class='refund-line'>" +
                        "           <span>充值优惠：</span>" +
                        "               满<ul id='vip-reach'></ul>送<ul id='vip-send'></ul>" +
                        "       </div>" +
                        "    </div>" +
                        /*
                        "    <div class='refund-coupon primary-bg'>" +
                        "        <span class='title'>退票策略包含退票时间、电影范围和退票范围 咱也不知道还有啥需求</span>" +
                        "    </div>" +
                        */
                        "</div>";




        /*
        vipDomStr+="<form class='form-horizontal' role='form'>"+
        "<div class='form-group'>"+
        "<label class='col-sm-3 control-label'>会员卡价格：</label>"+
        "<div class='col-sm-1'>"+
        "<span class='form' id='vip-price'></span>"+
        "</div>"+
        "<label class='col-sm-1 control-label'>元</label>"+
        "</div>"+
        "<div class='form-group'>"+
                "<label class='col-sm-3 control-label'>会员卡描述：</label>"+
                "<div class='col-sm-9'>"+
                "<span class='form' id='vip-description'></span>"+
                "</div>"+
                "</div>"+
        "<div class='form-group'>"+
                "<label class='col-sm-3 control-label'>购票优惠：</label>"+
                "<div class='col-sm-1'>"+
                "<span class='form' id='vip-discount'></span>"+
                "</div>"+
                "<label class='col-sm-1 control-label'>折</label>"+
                "</div>"+
        "<div class='form-group'>"+
                "<label class='col-sm-3 control-label'>满：</label>"+
                "<div class='col-sm-1'>"+
                "<span class='form' id='vip-reach'></span>"+
                "</div>"+
                "<div class='col-sm-1'>"+
                "<label class='col-sm-1 control-label'>送：</label>"+
                "</div>"+
                "<div class='col-sm-1'>"+
                "<span class='form' id='vip-send'></span>"+
                "</div>"+
                "</div>";


/*
        "<div style='text-align: center'>"+
        "<span>会员卡价格：</span>"+
        "<span id='vip-price'></span>"+
        "<span>元</span>"+
        "</div>"+
        "<div style='text-align: center'>"+
        "<span>会员卡描述：</span>"+
        "<span id='vip-description'></span>"+
        "</div>"+
        "<div style='text-align: center'>"+
        "<span>购票优惠：</span>"+
        "<span id='vip-discount'></span>"+
        "<span>折</span>"+
        "</div>"+
        "<div style='text-align: center'>"+
        "<span>满</span>"+
        "<span id='vip-reach'></span>"+
        "<span>送</span>"+
        "<span id='vip-send'></span>"+
        "</div>";
        */
        $(".content-vip").append(vipDomStr);
        $('#vip-price').text(vip.price);
        $('#vip-description').text(vip.description);
        $('#vip-discount').text(vip.discount);
        $('#vip-reach').text(vip.reach);
        $('#vip-send').text(vip.send);
    }

/*


                    <div class="form-group">
                        <label class="col-sm-3 control-label">本次余额变动：</label>
                        <div class="col-sm-9">
                            <span class="form" id="vamount"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">消费后余额：</label>
                        <div class="col-sm-9">
                            <span class="form" id="vafter_balance"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">消费时间：</label>
                        <div class="col-sm-9">
                            <span class="form" id="vjoin_time"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">消费原因：</label>
                        <div class="col-sm-9">
                            <span class="form" id="vpay_reason"></span>
                        </div>
                    </div>
                </form>
*/


    $("#vip-form-btn").click(function () {
        var form = {
           price: $("#vip-price-input").val(),
           description: $("#vip-description-input").val(),
           discount: $("#vip-discount-input").val(),
           reach: $("#vip-charge-reach").val(),
           send:  $("#vip-charge-send").val()
        };
        if(!validateVIPForm(form)){
            return;
        }
        if(form.discount==""){
            form.discount=10;
        }
        if(form.reach==""||form.reach==0){
            form.reach=100;
        }
        if(form.send==""){
            form.reach=100;
            form.send=0;
        }
        postRequest(
            '/vip/publish',
            form,
            function (res) {
                if(res.success){
                    getVIPInfo();
                    $("#vipModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
        window.location.reload();
    });

    $("#modify-vip-btn").click(function(){
		getRequest(
            '/vip/getVIPInfo',
            function(res){
                price: $("#vipm-price-input").val(res.content.price);
                description: $("#vipm-description-input").val(res.content.description);
                discount: $("#vipm-discount-input").val(res.content.discount);
                reach: $("#vipm-charge-reach").val(res.content.reach);
                send:  $("#vipm-charge-send").val(res.content.send);
            },
            function (error) {
                alert(error);
            }
        );
    });

    $("#vipm-form-btn").click(function () {
           var form = {
               price: $("#vipm-price-input").val(),
               description: $("#vipm-description-input").val(),
               discount: $("#vipm-discount-input").val(),
               reach: $("#vipm-charge-reach").val(),
               send:  $("#vipm-charge-send").val()
           };
        if(!validatemVIPForm(form)){
            return;
        }
        if(form.discount==""){
              form.discount=10;
        }
        if(form.reach==""||form.reach==0){
              form.reach=100;
        }
        if(form.send==""){
              form.reach=100;
              form.send=0;
        }
            postRequest(
                '/vip/modify',
                form,
                function (res) {
                    if(res.success){
                        getVIPInfo();
                        $("#vip-modify-Modal").modal('hide');
                    } else {
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
            window.location.reload();
        });

        function validateVIPForm(data) {
            var isValidate = true;
            if(!data.price) {
                 isValidate = false;
                 $('#vip-price-input').parent('.form-group').addClass('has-error');
                 $('#vip-price-error').css("visibility", "visible");
            }
            if(isNaN(data.discount)||data.discount<=0||data.discount>10) {
                 if(data.discount!=""){
                      isValidate = false;
                      $('#vip-discount-input').parent('.form-group').addClass('has-error');
                      $('#vip-discount-error').css("visibility", "visible");
                 }
            }
            if(isNaN(data.reach)&&data.reach!=""){
                 isValidate = false;
                 $('#vip-charge-reach').parent('.form-group').addClass('has-error');
                 $('#vip-charge-error').css("visibility", "visible");
            }
            else if(isNaN(data.send)&&data.send!=""){
                 isValidate = false;
                 $('#vip-charge-reach').parent('.form-group').addClass('has-error');
                 $('#vip-charge-error').css("visibility", "visible");
            }
            else if((!isNaN(data.reach)&&data.reach<0)||(!isNaN(data.send)&&data.send<0)){
                 isValidate = false;
                 $('#vip-charge-reach').parent('.form-group').addClass('has-error');
                 $('#vip-charge-error').css("visibility", "visible");
            }
            else if((data.reach==0||data.reach=="")&&(data.send!=""&&data.send>0)){
                 isValidate = false;
                 $('#vip-charge-reach').parent('.form-group').addClass('has-error');
                 $('#vip-charge-error').css("visibility", "visible");
            }
            return isValidate;
        }

         function validatemVIPForm(data) {
                    var isValidate = true;
                    if(!data.price) {
                         isValidate = false;
                         $('#vipm-price-input').parent('.form-group').addClass('has-error');
                         $('#vipm-price-error').css("visibility", "visible");
                    }
                    if(isNaN(data.discount)||data.discount<=0||data.discount>10) {
                        if(data.discount!=""){
                             isValidate = false;
                             $('#vipm-discount-input').parent('.form-group').addClass('has-error');
                             $('#vipm-discount-error').css("visibility", "visible");
                        }
                    }
                    if(isNaN(data.reach)&&data.reach!=""){
                         isValidate = false;
                         $('#vipm-charge-reach').parent('.form-group').addClass('has-error');
                         $('#vipm-charge-error').css("visibility", "visible");
                    }
                    else if(isNaN(data.send)&&data.send!=""){
                         isValidate = false;
                         $('#vipm-charge-reach').parent('.form-group').addClass('has-error');
                         $('#vipm-charge-error').css("visibility", "visible");
                    }
                    else if((!isNaN(data.reach)&&data.reach<0)||(!isNaN(data.send)&&data.send<0)){
                        isValidate = false;
                        $('#vipm-charge-reach').parent('.form-group').addClass('has-error');
                        $('#vipm-charge-error').css("visibility", "visible");
                    }
                    else if((data.reach==0||data.reach=="")&&(data.send!=""&&data.send>0)){
                        isValidate = false;
                        $('#vipm-charge-reach').parent('.form-group').addClass('has-error');
                        $('#vipm-charge-error').css("visibility", "visible");
                    }
                    return isValidate;
                }

    function getVIPInfo(){
        getRequest(
            '/vip/getVIPInfo',
            function(res){
                vip=res.content;
                renderVIP(res.content);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }
});