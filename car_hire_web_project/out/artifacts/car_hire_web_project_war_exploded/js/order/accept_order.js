

function acceptOrder(orderId, userEmail, rentalPrice, adServicePrice) {

    var order_response = document.getElementById('order_response');


    var action = "ACCEPT_ORDER";

    var params = {
        action: action,

        orderId: orderId,
        email: userEmail,
        rentalPrice: rentalPrice,
        adServicePrice: adServicePrice
    };


    $.post("/orders", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            $("#order_response").text(responseText);
            window.location.href = "orders";
        }
    });

}
