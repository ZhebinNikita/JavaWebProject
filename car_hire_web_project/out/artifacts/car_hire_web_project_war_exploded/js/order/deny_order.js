function animDenyOrderStart() { $('#deny_order_loading').show(); }
function animDenyOrderStop() { $('#deny_order_loading').hide(); }

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



function denyOrder() {

    var adInfo = document.getElementById('ad_info');

    var incorrect_deny_order = document.getElementById('incorrect_deny_order');

    var action = "DENY_ORDER";

    var params = {
        action: action,

        id: deny_order_with_id,
        adInfo: adInfo.value
    };


    if(adInfo.value != ""){
        incorrect_deny_order.innerHTML = " ";
    }
    else{
        incorrect_deny_order.innerHTML = "";
        setMessage(incorrect_deny_order, "required.field", "");
    }


    if(incorrect_deny_order.innerHTML == " ") {
        animDenyOrderStart();
        $.post("/orders", $.param(params), function (responseText) {
            animDenyOrderStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_deny_order").text(responseText);
                window.location.href = "orders";
            }
        });
    }

}