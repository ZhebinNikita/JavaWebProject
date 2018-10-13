$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #rent-car-dialog-overlay').click(function () {
        $('#rent-car-dialog-overlay, #rent-car-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#rent-car-dialog-box').is(':hidden')) {
            openRentCarDialog();
        }
    });


});


var rentingCarId;
var rentingRentalPriceForDay;


function openRentCarDialog(carId, rentalPriceForDay) {


    rentingCarId = carId;
    rentingRentalPriceForDay = rentalPriceForDay;


    $('#rent-car-dialog-overlay').show();
    $('#rent-car-dialog-box').show();

}


function closeRentCarDialog() {

    $('#rent-car-dialog-overlay').hide();
    $('#rent-car-dialog-box').hide();

}