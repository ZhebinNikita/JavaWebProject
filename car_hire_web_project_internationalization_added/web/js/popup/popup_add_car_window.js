$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #add-car-dialog-overlay').click(function () {
        $('#add-car-dialog-overlay, #add-car-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#add-car-dialog-box').is(':hidden')) {
            openAddCarDialog();
        }
    });


});


function openAddCarDialog() {

    $('#add-car-dialog-overlay').show();
    $('#add-car-dialog-box').show();

}


function closeAddCarDialog() {

    $('#add-car-dialog-overlay').hide();
    $('#add-car-dialog-box').hide();

}
