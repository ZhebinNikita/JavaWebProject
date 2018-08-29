function loadAnimStart() { $('#loading').show(); }
function loadAnimStop() { $('#loading').hide(); }

function clickLogin() {

    var incorrect_text_email = document.getElementById('incorrect_text_email');
    var incorrect_text_pass = document.getElementById('incorrect_text_pass');
    var userEmail = document.getElementById('userEmail');
    var userPass = document.getElementById('userPass');

    var params = {
        email: userEmail.value,
        pass: userPass.value
    };


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

    // Call If email is present in DataBase or it's not
    if(incorrect_text_email.innerHTML == "" && incorrect_text_pass.innerHTML == ""){
        loadAnimStart();
        $.post("", $.param(params), function(responseText) {
            loadAnimStop();
            if(responseText == "Регистрация прошла успешно!"){
                $("#incorrect_text_email").text(responseText);
                window.location.href = "car_list"; // redirect to another page.
            }
            else{
                $("#incorrect_text_email").text(responseText);
            }
        });
    }

}