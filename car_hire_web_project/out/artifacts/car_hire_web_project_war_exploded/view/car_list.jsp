<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <link href="../css/popup_add_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../script/popup_window.js"></script>

    <script type="text/javascript" src="../script/add_car_click.js"></script>

</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Список автомобилей</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">

        <div align="right">
            <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom" onclick="popup()">
                Добавить Авто
            </button>
        </div>

        <div class="w3-container w3-light-blue">

            <h2>Автомобили на прокат</h2>

        </div>



        <c:set var="cars" value="cars" />


        <c:if test="${requestScope.get(cars) != null && not empty requestScope.get(cars)}">
            <ul class=\"w3-ul\">
            <c:forEach items="${requestScope.get(cars)}" var="car" >
                <li class=\"w3-hover-sand\">
                        ${car.getName()} --- ${car.getDailyRentalPrice()} USD --- ${car.getCarClass()} --- ${car.getRented()}
                </li>
            </c:forEach>
            </ul>
        </c:if>

        <c:if test="${requestScope.get(cars) == null || empty requestScope.get(cars)}">
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right
                      w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×
                </span>
                <h5>Список пуст!</h5>
            </div>
        </c:if>



        <!-- ---------- Popup Window ---------- -->
        <div id="dialog-overlay"></div>
        <div id="dialog-box">
            <a class="button" onclick="popdown()">X</a>
            <div class="dialog-content">
                <!--------------------------------->
                <p align="center">
    <span style="color: #333333; font-size: 22px; font-weight: 700;">
        Добавление Автомобиля</span></p>


                <p align="center">
                    <label>
                        <input type="text" name="carName"
                               style="width: 30%" id="carName" maxlength="20" placeholder="Название автомобиля">
                    </label>
                </p>
                <p align="center" style='color:red;' id="incorrect_name"></p>

                <p align="center">
                    <label>
                        <input type="number" name="carDailyRentalPrice"
                               style="width: 30%" id="carDailyRentalPrice" maxlength="20" placeholder="Цена аренды USD/день">
                    </label>
                </p>
                <p align="center" style='color:red;' id="incorrect_price"></p>


                <p align="center">
                    <span>
                        <select id="car_class_selection">
                            <option>Mini</option>
                            <option>Economy</option>
                            <option>Compact</option>
                            <option>Average</option>
                            <option>Standart</option>
                            <option>Family</option>
                            <option>Special</option>
                            <option>Premium</option>
                            <option>Lux</option>
                            <option>LargeSize</option>
                        </select>
                    </span>
                </p>


                <p align="center">
                    <button name="btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="login_btn" onclick="clickAddCar()">
                        Добавить автомобиль
                    </button>
                </p>

                <div id="loading" hidden>
                    <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />   Пожалуйста подождите...</p>
                </div>
                <!--------------------------------->
            </div>
        </div>
        <!-- ---------- Popup Window ---------- -->


    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Назад</button>
</div>


</body>
</html>
