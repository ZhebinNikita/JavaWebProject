
function logout() {

    var action = "LOGOUT";

    var params = {
        action: action
    };

    $.post("", $.param(params), function (responseText) {

        window.location.href = "/ "; // redirect to another page.

    });
}