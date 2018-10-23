function animRentCarStart() { $('#rent_car_loading').show(); }
function animRentCarStop() { $('#rent_car_loading').hide(); }

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

function redirectIfEquals(someStr, key, urlReq, urlRedirect) {

    var action = "SET_LANG_JS_MESSAGE";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(urlReq, $.param(params), function (responseText) {

        if(someStr == responseText) {
            window.location.href = urlRedirect; // redirect to another page.
        }

    });
}



var rentingCarId;
var rentingRentalPriceForDay;

function rentCar(renterEmail, rentingAdServicePrice) {


    var incorrect_receiving_date = document.getElementById('incorrect_receiving_date');
    var incorrect_return_date = document.getElementById('incorrect_return_date');
    var incorrect_renter_name = document.getElementById('incorrect_renter_name');
    var incorrect_renter_surname = document.getElementById('incorrect_renter_surname');
    var incorrect_renter_birthday = document.getElementById('incorrect_renter_birthday');
    var incorrect_renter_id_number = document.getElementById('incorrect_renter_id_number');

    var receiving_date = document.getElementById('receiving_date');
    var return_date = document.getElementById('return_date');
    var renter_name = document.getElementById('renter_name');
    var renter_surname = document.getElementById('renter_surname');
    var renter_birthday = document.getElementById('renter_birthday');
    var renter_id_number = document.getElementById('renter_id_number');


    var action = "REQUEST_RENT_CAR";

    var params = {
        action: action,

        // Order data
        email: renterEmail,
        carId: rentingCarId,

        receiving_date: receiving_date.value,
        return_date: return_date.value,

        // rental price of the car we'll take from database by id
        rentalPrice: rentingRentalPriceForDay,
        adServicePrice: rentingAdServicePrice,

        orderIsPaid: 0, // default - is not paid.
        adInfo: "", // This info will display to user, when admin accept the request

        // Passport data
        renter_name: renter_name.value,
        renter_surname: renter_surname.value,
        renter_birthday: renter_birthday.value,
        renter_id_number: renter_id_number.value
    };



    /////////////////////  Validation  /////////////////////
    if (!/[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])/.test(receiving_date.value)) {
        setMessage(incorrect_receiving_date, "validation.is.failed", "");
    } else {
        incorrect_receiving_date.innerHTML = " ";
    }

    if (!/[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])/.test(return_date.value)) {
        setMessage(incorrect_return_date, "validation.is.failed", "");
    } else {
        incorrect_return_date.innerHTML = " ";
    }

    if (/ /.test(renter_name.value) || renter_name.value == ""
        || renter_name.value.toString().length < 2 || renter_name.value.toString().length > 45) {
        setMessage(incorrect_renter_name, "validation.is.failed", "");
    } else {
        incorrect_renter_name.innerHTML = " ";
    }

    if (/ /.test(renter_surname.value) || renter_surname.value == ""
        || renter_surname.value.toString().length < 2 || renter_surname.value.toString().length > 45) {
        setMessage(incorrect_renter_surname, "validation.is.failed", "");
    } else {
        incorrect_renter_surname.innerHTML = " ";
    }

    if (!/[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])/.test(renter_birthday.value)) {
        setMessage(incorrect_renter_birthday, "validation.is.failed", "");
    } else {
        incorrect_renter_birthday.innerHTML = " ";
    }

    if (/ /.test(renter_id_number.value) || renter_id_number.value == "") {
        setMessage(incorrect_renter_birthday, "validation.is.failed", "");
    } else {
        incorrect_renter_id_number.innerHTML = " ";
    }
    /////////////////////  Validation  /////////////////////



    if (incorrect_receiving_date.innerHTML == " " && incorrect_return_date.innerHTML == " "
        && incorrect_renter_name.innerHTML == " " && incorrect_renter_surname.innerHTML == " "
        && incorrect_renter_birthday.innerHTML == " " && incorrect_renter_id_number.innerHTML == " ") {
        animRentCarStart();
        $.post("/car_list", $.param(params), function (responseText) {
            animRentCarStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_renter_id_number").text(responseText);
                redirectIfEquals(responseText, "request.sent", "", "/profile");
            }
        });
    }

}
