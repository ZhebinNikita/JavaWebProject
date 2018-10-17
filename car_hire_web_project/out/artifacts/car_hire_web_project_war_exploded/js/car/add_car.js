function animAddCarStart() { $('#add_car_loading').show(); }
function animAddCarStop() { $('#add_car_loading').hide(); }

function setMessage(element, key, url) {

    var action = "SET_LANG_JS_MESSAGE";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(url, $.param(params), function (responseText) {
        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            element.innerHTML = responseText;
        }
    });
}

function addCar() {

    var incorrect_name = document.getElementById('incorrect_car_name');
    var incorrect_price = document.getElementById('incorrect_car_price');
    var incorrect_amount = document.getElementById('incorrect_car_amount');

    var carName = document.getElementById('adding_carName');
    var carDailyRentalPrice = document.getElementById('adding_carDailyRentalPrice');
    var amount_cars = document.getElementById('adding_amount_cars');

    var action = "ADD_CAR";

    var params = {
        action: action,
        name: carName.value,
        daily_rental_price: carDailyRentalPrice.value,
        car_class: $("#adding_car_class_selection option:selected").text(),
        amount_cars: amount_cars.value
    };


    if(carName.value != ""){
        incorrect_name.innerHTML = " ";
    }
    else{
        setMessage(incorrect_name, "required.field", "");
    }

    if(carDailyRentalPrice.value != "" && carDailyRentalPrice.value > 0){
        incorrect_price.innerHTML = " ";
    }
    else{
        setMessage(incorrect_price, "required.field", "");
    }

    if(amount_cars.value != "" && !/-/.test(amount_cars.value)
        && amount_cars.value > 0 && amount_cars.value <= 100){
        incorrect_amount.innerHTML = " ";
    }
    else{
        setMessage(incorrect_amount, "range.adding.cars", "");
    }


    //if(incorrect_name.innerHTML == " " && incorrect_price.innerHTML == " " && incorrect_amount.innerHTML == " "){
        animAddCarStart();
        $.post("/car_list", $.param(params), function(responseText) {
            animAddCarStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_car_name").text(responseText);
                window.location.href = "car_list";
            }
        });
    //}

}