
function deleteCar(id) {

    var action = "DELETE_CAR";

    var params = {
        action: action,
        id: id
    };

    $.post("/car_list", $.param(params), function(responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            window.location.href = "car_list"; // redirect to another page.
        }
    });

}