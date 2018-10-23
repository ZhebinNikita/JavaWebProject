function animUpdateBalanceStart() { $('#update_balance_loading').show(); }
function animUpdateBalanceStop() { $('#update_balance_loading').hide(); }

function setMessage(element, key, url) {

    var action = "SET_LANG_JS_MESSAGE";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(url, $.param(params), function (responseText) {
        if (responseText == "ERROR") {
            window.location.href = "error_page";
        }
        else {
            element.innerHTML = responseText;
        }
    });
}

function redirectIfEquals(someStr, key, urlReq, urlRedirect) {

    var action = "SET_LANG_JS_MESSAGE";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(urlReq, $.param(params), function (responseText) {

        if(someStr == responseText) {
            window.location.href = urlRedirect; // redirect to another page.
        }

    });
}



function updateBalance(email) {

    var amount_of_money = document.getElementById('amount_of_money');
    var incorrect_amount_of_money = document.getElementById('incorrect_amount_of_money');

    var action = "UPDATE_BALANCE";

    var params = {
        action: action,
        email: email,
        amount: amount_of_money.value
    };


    if(amount_of_money.value != "" && amount_of_money.value > 0){
        incorrect_amount_of_money.innerHTML = " ";
    }
    else{
        setMessage(incorrect_amount_of_money, "required.field", "");
    }

    if(incorrect_amount_of_money.innerHTML == " ") {
        animUpdateBalanceStart();
        $.post("/profile", $.param(params), function (responseText) {
            animUpdateBalanceStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_amount_of_money").text(responseText);
                redirectIfEquals(responseText, "operation.completed.successfully", "", "/profile");
            }
        });
    }
}