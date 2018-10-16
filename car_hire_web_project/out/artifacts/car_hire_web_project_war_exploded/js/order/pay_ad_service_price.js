

function payAdServicePrice(orderId, email) {

    var paid_order_response = document.getElementById('paid_order_response');


    var action = "PAY_AD_SERVICE_PRICE";

    var params = {
        action: action,

        orderId: orderId,
        email: email
    };


    $.post("/profile", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            $("#paid_order_response").text(responseText);
            window.location.href = "profile";
        }
    });

}