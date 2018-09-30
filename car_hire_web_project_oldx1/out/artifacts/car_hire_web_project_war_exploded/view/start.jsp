<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>

    <title> Lalala </title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <link href="../css/popup_login_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_login_window.js"></script>

    <script type="text/javascript" src="../js/add_user.js"></script>

</head>

<body class="w3-light-grey">


<!-- ---------- Popup Window ---------- -->
<div id="login-dialog-overlay"></div>
<div id="login-dialog-box">
    <a class="login-btn-close" onclick="closeLoginDialog()">X</a>
    <div class="login-dialog-content">
        <!--------------------------------->
        <p align="center">
    <span style="color: #333333; font-size: 22px; font-weight: 700;">
        Войти или зарегистрироваться</span></p>


        <p align="center">
            <label>
                <input type="text" name="userEmail"
                       style="width: 30%" id="userEmail" maxlength="40" placeholder="Адрес электронной почты">
            </label>
        </p>
        <p align="center" style='color:red;' id="incorrect_text_email"></p>

        <p align="center">
            <label>
                <input type="password" name="userPass"
                       style="width: 30%" id="userPass" maxlength="40" placeholder="Пароль">
            </label>
        </p>
        <p align="center" style='color:red;' id="incorrect_text_pass"></p>


        <p align="center">
            <button name="btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="login_btn" onclick="clickLogin()">
                Войти / Зарегистрироваться
            </button>
        </p>

        <div id="loading" hidden>
            <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />   Пожалуйста подождите...</p>
        </div>
        <!--------------------------------->
    </div>
</div>
<!-- ---------- Popup Window ---------- -->


<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1> Rent Cars </h1>
</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">
        <button class="w3-btn w3-hover-light-blue w3-round-large w3-margin-bottom" onclick="location.href='/car_list'">
            List of cars to rent
        </button>
        <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom" onclick="openLoginDialog()">
            Войти / Зарегистрироваться
        </button>
    </div>
</div>


</body>


</html>
