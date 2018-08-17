function loadAnimStart() { $('#loading').show(); }
function loadAnimStop() { $('#loading').hide(); }

function clickAddCar() {

    var incorrect_name = document.getElementById('incorrect_name');
    var incorrect_price = document.getElementById('incorrect_price');
    var carName = document.getElementById('carName');
    var carDailyRentalPrice = document.getElementById('carDailyRentalPrice');

    var params = {
        name: carName.value,
        daily_rental_price: carDailyRentalPrice.value,
        car_class: $("#car_class_selection option:selected").text()
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


    if(incorrect_name.innerHTML == "" && incorrect_price.innerHTML == ""){
        loadAnimStart();
        $.post("/car_list", $.param(params), function(responseText) {
            loadAnimStop();
            if(responseText == "Автомобиль добавлен!"){
                $("#incorrect_name").text(responseText);
                window.location.href = "car_list"; // redirect to another page.
            }
            else{
                $("#incorrect_name").text(responseText);
            }
        });
    }

}