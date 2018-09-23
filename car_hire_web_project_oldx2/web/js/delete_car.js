
function deleteCar(id) {

    var action = "delete_car";

    var params = {
        action: action,

        id: id
    };

    $.post("/car_list", $.param(params), function(responseText) {
        // some response actions
        if(responseText == "Car deleted!")
        window.location.href = "car_list"; // redirect to another page.
    });

}