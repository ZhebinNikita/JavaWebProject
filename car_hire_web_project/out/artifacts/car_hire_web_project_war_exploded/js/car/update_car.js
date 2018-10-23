function animUpdateCarStart() { $('#update_car_loading').show(); }
function animUpdateCarStop() { $('#update_car_loading').hide(); }


function updateCar(id) {

    var carName = document.getElementById('updating_carName');
    var carDailyRentalPrice = document.getElementById('updating_carDailyRentalPrice');

    var updating_incorrect_name = document.getElementById('updating_incorrect_name');
    var updating_incorrect_price = document.getElementById('updating_incorrect_price');


    var action = "UPDATE_CAR";

    var params = {
        action: action,

        id: id,
        name: carName.value,
        daily_rental_price: carDailyRentalPrice.value,
        car_class: $("#updating_car_class_selection option:selected").text()
    };



    /////////////////////  Validation  /////////////////////
    if(carName.value != ""){
        updating_incorrect_name.innerHTML = " ";
    }
    else{
        setMessage(updating_incorrect_name, "required.field", "");
    }

    if(carDailyRentalPrice.value != "" && carDailyRentalPrice.value > 0){
        updating_incorrect_price.innerHTML = " ";
    }
    else{
        setMessage(updating_incorrect_price, "required.field", "");
    }
    /////////////////////  Validation  /////////////////////



    if(updating_incorrect_name.innerHTML == " " && updating_incorrect_price.innerHTML == " ") {
        animUpdateCarStart();
        $.post("/car_list", $.param(params), function (responseText) {
            animUpdateCarStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#updating_incorrect_name").text(responseText);
                window.location.href = "car_list";
            }
        });
    }

}