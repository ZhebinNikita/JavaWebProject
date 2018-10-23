function animAddAdServicePriceStart() { $('#add_ad_service_price_loading').show(); }
function animAddAdServicePriceStop() { $('#add_ad_service_price_loading').hide(); }

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



function addAdServicePrice() {

    var adServicePrice = document.getElementById('ad_service_price');
    var incorrect_adServicePrice = document.getElementById('incorrect_ad_service_price');

    var action = "ADD_AD_SERVICE_PRICE";

    var params = {
        action: action,

        id: add_ad_service_price_with_id,
        adServicePrice: adServicePrice.value
    };


    /////////////////////  Validation  /////////////////////
    if(adServicePrice.value != "" && adServicePrice.value >= 0){
        incorrect_adServicePrice.innerHTML = " ";
    }
    else{
        incorrect_adServicePrice.innerHTML = "";
        setMessage(incorrect_adServicePrice, "required.field", "");
    }
    /////////////////////  Validation  /////////////////////


    if(incorrect_adServicePrice.innerHTML == " ") {
        animAddAdServicePriceStart();
        $.post("/orders", $.param(params), function (responseText) {
            animAddAdServicePriceStop();

            if (responseText == "ERROR") {
                window.location.href = "error_page";
            }
            else {
                $("#incorrect_adServicePrice").text(responseText);
                window.location.href = "orders?orders=paid";
            }
        });
    }
}