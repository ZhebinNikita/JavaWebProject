
function logout() {

    var action = "LOGOUT";

    var params = {
        action: action
    };

    $.post("", $.param(params), function (responseText) {

        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            window.location.href = "/ "; // redirect to another page.
        }
    });
}