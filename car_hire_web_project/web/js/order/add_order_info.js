function animAddOrderInfoStart() { $('#add_order_info_loading').show(); }
function animAddOrderInfoStop() { $('#add_order_info_loading').hide(); }

function setMessage(element, key, url) {

    var action = "SET_LANG_JS_MESSAGE";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(url, $.param(params), function (responseText) {
        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            element.innerHTML = responseText;
        }
    });
}



function addOrderInfo() {

    var adInfo = document.getElementById('order_info');

    var incorrect_add_order_info = document.getElementById('incorrect_add_order_info');

    var action = "ADD_ORDER_INFO";

    var params = {
        action: action,

        id: add_order_info_with_id,
        adInfo: adInfo.value
    };


    if(adInfo.value != ""){
        incorrect_add_order_info.innerHTML = " ";
    }
    else{
        incorrect_add_order_info.innerHTML = "";
        setMessage(incorrect_add_order_info, "required.field", "");
    }


    if(incorrect_add_order_info.innerHTML == " ") {
        animAddOrderInfoStart();
        $.post("/orders", $.param(params), function (responseText) {
            animAddOrderInfoStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_add_order_info").text(responseText);
                window.location.href = "orders?orders=paid";
            }
        });
    }

}