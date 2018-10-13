

function acceptOrder(orderId, userEmail, rentalPrice, adServicePrice) {


    var action = "ACCEPT_ORDER";

    var params = {
        action: action,

        orderId: orderId,
        userEmail: userEmail,
        rentalPrice: rentalPrice,
        adServicePrice: adServicePrice
    };


    $.post("/orders", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            window.location.href = "orders";
        }
    });

}
