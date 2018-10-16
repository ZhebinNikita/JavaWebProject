$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #deny-order-dialog-overlay').click(function () {
        $('#deny-order-dialog-overlay, #deny-order-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#deny-order-dialog-box').is(':hidden')) {
            openDenyOrderDialog();
        }
    });


});


var deny_order_with_id;

function openDenyOrderDialog(id) {

    deny_order_with_id = id;

    $('#deny-order-dialog-overlay').show();
    $('#deny-order-dialog-box').show();

}


function closeDenyOrderDialog() {

    $('#deny-order-dialog-overlay').hide();
    $('#deny-order-dialog-box').hide();

}