$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #add-order-info-dialog-overlay').click(function () {
        $('#add-order-info-dialog-overlay, #add-order-info-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#add-order-info-dialog-box').is(':hidden')) {
            openAddOrderInfoDialog();
        }
    });


});


var add_order_info_with_id;

function openAddOrderInfoDialog(id) {

    add_order_info_with_id = id;

    $('#add-order-info-dialog-overlay').show();
    $('#add-order-info-dialog-box').show();

}


function closeAddOrderInfoDialog() {

    $('#add-order-info-dialog-overlay').hide();
    $('#add-order-info-dialog-box').hide();

}