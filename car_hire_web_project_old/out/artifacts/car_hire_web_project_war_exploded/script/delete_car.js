function loadAnimStart() { $('#loading').show(); }
function loadAnimStop() { $('#loading').hide(); }

function deleteCar(id) {

    var params = {
        action: "delete_car",

        id: id
    };

    $.post("/car_list", $.param(params), function(responseText) {
        // some response actions
        if(responseText == "Car deleted!")
        window.location.href = "car_list"; // redirect to another page.
    });

}