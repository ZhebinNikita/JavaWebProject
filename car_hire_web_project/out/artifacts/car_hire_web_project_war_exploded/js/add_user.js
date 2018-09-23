function animLoginStart() { $('#loading').show(); }
function animLoginStop() { $('#loading').hide(); }

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
    if(!/@/.test(userEmail.value) && userEmail.value != ""){
        incorrect_text_email.innerHTML = "Неверный email";
    }
    else if(/@/.test(userEmail.value)){
        incorrect_text_email.innerHTML = "";
    }
    else{
        incorrect_text_email.innerHTML = "Обязательное поле";
    }

    if(userPass.value.toString().length < 8 && userPass.value != "") {
        incorrect_text_pass.innerHTML = "Пароль должен состоять не менее чем из 8 символов.";
    }
    else if(userPass.value.toString().length >= 8){
        incorrect_text_pass.innerHTML = "";
    }
    else{
        incorrect_text_pass.innerHTML = "Обязательное поле";
    }
    /////////////////////  Validation  /////////////////////


    if(incorrect_text_email.innerHTML == "" && incorrect_text_pass.innerHTML == ""){
        animLoginStart();
        $.post("", $.param(params), function(responseText) {
            animLoginStop();

                $("#incorrect_text_email").text(responseText);
                //window.location.href = "car_list"; // redirect to another page.

        });
    }

}