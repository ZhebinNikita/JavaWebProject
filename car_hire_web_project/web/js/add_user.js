function animLoginStart() { $('#loading').show(); }
function animLoginStop() { $('#loading').hide(); }

function setMessage(element, key, url) {

    var action = "set_lang_js_message";

    var params = {
        action: action,
        lang_key: key
    };

    $.post(url, $.param(params), function (responseText) {
        element.innerHTML = responseText;
    });

}

function clickLogin() {

    var incorrect_text_email = document.getElementById('incorrect_text_email');
    var incorrect_text_pass = document.getElementById('incorrect_text_pass');
    var userEmail = document.getElementById('userEmail');
    var userPass = document.getElementById('userPass');

    var action = "add_user";

    var params = {
        action: action,
        email: userEmail.value,
        pass: userPass.value
    };


    /////////////////////  Validation  /////////////////////
    if(!/@/.test(userEmail.value) && userEmail.value != "" ){
        setMessage(incorrect_text_email, "wrong.email", "");
    }
    else if(/@/.test(userEmail.value)){
        incorrect_text_email.innerHTML = " ";
    }
    else{
        setMessage(incorrect_text_email, "required.field", "");
    }

    // ADD CHECKING EMPTY SPACES
    if(userPass.value.toString().length < 8 && userPass.value != "" ) {
        setMessage(incorrect_text_pass, "password.requirements", "");
    }
    else if(userPass.value.toString().length >= 8){
        incorrect_text_pass.innerHTML = " ";
    }
    else{
        setMessage(incorrect_text_pass, "required.field", "");
    }
    /////////////////////  Validation  /////////////////////


    if(incorrect_text_email.innerHTML == " " && incorrect_text_pass.innerHTML == " ") {
        animLoginStart();
        $.post("", $.param(params), function (responseText) {
            animLoginStop();

            $("#incorrect_text_email").text(responseText);
            //window.location.href = "car_list"; // redirect to another page.

        });
    }

}


