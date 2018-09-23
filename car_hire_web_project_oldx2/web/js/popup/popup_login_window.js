$(document).ready(function () {

    // if user clicked on button, the overlay layer or the dialogbox, close the dialog
    $('a.btn-ok, #login-dialog-overlay').click(function () {
        $('#login-dialog-overlay, #login-dialog-box').hide();
        return false;
    });

    // if user resize the window, call the same function again
    // to make sure the overlay fills the screen and dialogbox aligned to center
    $(window).resize(function () {
        //only do it if the dialog box is not hidden
        if (!$('#login-dialog-box').is(':hidden')) openLoginDialog();
    });


});


function openLoginDialog() {

    // get the screen height and width
    /*var maskHeight = $(document).height();
    var maskWidth = $(window).width();

    // calculate the values for center alignment
    var dialogTop =  (maskHeight/1.5) - ($('#dialog-box').height());
    var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2);

    // assign values to the overlay and dialog box
    $('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
    $('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();*/

    $('#login-dialog-overlay').show();
    $('#login-dialog-box').show();

    // display the message
    //$('#dialog-message').html(message);

}


function closeLoginDialog() {

    $('#login-dialog-overlay').hide();
    $('#login-dialog-box').hide();

}
