function refreshBalance(email) {


    var action = "REFRESH_BALANCE";

    var params = {
        action: action,
        email: email
    };


    $.post("/profile", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            window.location.href = "profile";
        }
    });
}