

function deleteOrder(id) {


    var action = "DELETE_ORDER";

    var params = {
        action: action,

        id: id
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