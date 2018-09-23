function animUpdateCarStart() { $('#update_car_loading').show(); }
function animUpdateCarStop() { $('#update_car_loading').hide(); }


function updateCar(id) {

    var carName = document.getElementById('updating_carName');
    var carDailyRentalPrice = document.getElementById('updating_carDailyRentalPrice');

    var action = "update_car";

    var params = {
        action: action,

        id: id,

        name: carName.value,
        daily_rental_price: carDailyRentalPrice.value,
        car_class: $("#updating_car_class_selection option:selected").text()
    };

    animUpdateCarStop();
    $.post("/car_list", $.param(params), function(responseText) {
        animUpdateCarStop();
        // some response actions
        if (responseText == "Car updated!") {
            $("#updating_incorrect_name").text(responseText);
            window.location.href = "car_list"; // redirect to another page.
        }
        else {
            $("#updating_incorrect_name").text(responseText);
        }
    });

}