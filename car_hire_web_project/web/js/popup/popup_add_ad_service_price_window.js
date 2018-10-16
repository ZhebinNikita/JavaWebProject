$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #add-ad-service-price-dialog-overlay').click(function () {
        $('#add-ad-service-price-dialog-overlay, #add-ad-service-price-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#add-ad-service-price-dialog-box').is(':hidden')) {
            openAddAdServicePriceDialog();
        }
    });


});


var add_ad_service_price_with_id;


function openAddAdServicePriceDialog(id) {

    add_ad_service_price_with_id = id;

    $('#add-ad-service-price-dialog-overlay').show();
    $('#add-ad-service-price-dialog-box').show();

}


function closeAddAdServicePriceDialog() {

    $('#add-ad-service-price-dialog-overlay').hide();
    $('#add-ad-service-price-dialog-box').hide();

}