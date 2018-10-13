$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #update-balance-dialog-overlay').click(function () {
        $('#update-balance-dialog-overlay, #update-balance-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#update-balance-dialog-box').is(':hidden')) {
            openUpdateBalanceDialog();
        }
    });


});


function openUpdateBalanceDialog() {

    $('#update-balance-dialog-overlay').show();
    $('#update-balance-dialog-box').show();

}


function closeUpdateBalanceDialog() {

    $('#update-balance-dialog-overlay').hide();
    $('#update-balance-dialog-box').hide();

}