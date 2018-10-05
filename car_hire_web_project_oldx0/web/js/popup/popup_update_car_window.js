$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #update-car-dialog-overlay').click(function () {
        $('#update-car-dialog-overlay, #update-car-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#update-car-dialog-box').is(':hidden')) {
            openUpdateCarDialog();
        }
    });


});


var updatingCarId;
var updatingCarName;
var updatingCarRentalPrice;
var updatingCarClass;


function openUpdateCarDialog(id, name, rentalPrice, carClass) {

    updatingCarId = id;
    updatingCarName = name;
    updatingCarRentalPrice = rentalPrice;
    updatingCarClass = carClass;


    $('#update-car-dialog-overlay').show();
    $('#update-car-dialog-box').show();


    // Filling in empty fields
    var carName = document.getElementById('updating_carName');
    var carDailyRentalPrice = document.getElementById('updating_carDailyRentalPrice');
    var carClassSelection = document.getElementById('updating_car_class_selection');

    carName.value = name;
    carDailyRentalPrice.value = rentalPrice;
    carClassSelection.value = carClass;

}


function closeUpdateCarDialog() {

    $('#update-car-dialog-overlay').hide();
    $('#update-car-dialog-box').hide();

}
