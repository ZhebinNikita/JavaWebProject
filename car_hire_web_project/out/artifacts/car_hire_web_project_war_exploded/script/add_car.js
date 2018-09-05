function loadAnimStart() { $('#loading').show(); }
function loadAnimStop() { $('#loading').hide(); }

function clickAddCar() {

    var incorrect_name = document.getElementById('incorrect_name');
    var incorrect_price = document.getElementById('incorrect_price');
    var incorrect_amount = document.getElementById('incorrect_amount');

    var carName = document.getElementById('carName');
    var carDailyRentalPrice = document.getElementById('carDailyRentalPrice');
    var amount_cars = document.getElementById('amount_cars');

    var params = {
        action: "add_car",

        name: carName.value,
        daily_rental_price: carDailyRentalPrice.value,
        car_class: $("#car_class_selection option:selected").text(),
        amount_cars: amount_cars.value
    };


    if(carName.value != ""){
        incorrect_name.innerHTML = "";
    }
    else{
        incorrect_name.innerHTML = "Обязательное поле";
    }

    if(carDailyRentalPrice.value != ""){
        incorrect_price.innerHTML = "";
    }
    else{
        incorrect_price.innerHTML = "Обязательное поле";
    }

    if(amount_cars.value != "" && !/-/.test(amount_cars.value) && amount_cars.value != "0"){
        incorrect_amount.innerHTML = "";
    }
    else{
        incorrect_amount.innerHTML = "Обязательное поле. Min value = 1";
    }


    if(incorrect_name.innerHTML == "" && incorrect_price.innerHTML == "" && incorrect_amount.innerHTML == ""){
        loadAnimStart();
        $.post("/car_list", $.param(params), function(responseText) {
            loadAnimStop();
            if(responseText == "Car added!"){
                $("#incorrect_name").text(responseText);
                window.location.href = "car_list"; // redirect to another page.
            }
            else{
                $("#incorrect_name").text(responseText);
            }
        });
    }

}