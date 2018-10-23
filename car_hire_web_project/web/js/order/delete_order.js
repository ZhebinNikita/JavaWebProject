

function deleteOrder(id) {


    var action = "DELETE_ORDER";

    var params = {
        action: action,

        id: id
    };


    // DELETE ONLY IF RENTER AD SERVICE PRICE = 0 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    $.post("/orders", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            window.location.href = "orders?orders=paid";
        }
    });

}