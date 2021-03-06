<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="l" value="language"/>
<c:set var="language"
       value="${sessionScope.get(l)}"
       scope="session" />
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="lang" scope="session" />

<html>
<head>
    <c:set var="email" value="email"/>
    <c:set var="userEmail" value="${sessionScope.get(email)}"/>
    <c:set var="role" value="role"/>
    <c:set var="userRole" value="${sessionScope.get(role)}"/>
    <c:set var="balance" value="balance"/>
    <c:set var="accountBalance" value="${sessionScope.get(balance)}"/>
    <title> <fmt:message key="car.list"/> </title>

    <c:set var="carState" value="notRented"/>
    <c:set var="notRented" value="notRented"/>
    <c:set var="rented" value="rented"/>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <link href="../css/popup_add_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_add_car_window.js"></script>

    <link href="../css/popup_update_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_update_car_window.js"></script>

    <link href="../css/popup_rent_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_rent_car_window.js"></script>

    <script type="text/javascript" src="../js/car/add_car.js"></script>
    <script type="text/javascript" src="../js/car/delete_car.js"></script>
    <script type="text/javascript" src="../js/car/update_car.js"></script>
    <script type="text/javascript" src="../js/car/rent_car.js"></script>

</head>

<body class="w3-light-grey">
User: ${userEmail} Role: ${userRole}

<span>
    <fmt:message key="balance"/> ${accountBalance}$
</span>

<div class="w3-container w3-blue-grey w3-opacity">

    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru_RU" value="en" ${language == 'ru_RU' ? 'selected' : ''}>Russian</option>
        </select>
    </form>

    <h1><fmt:message key="car.list"/></h1>

</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">

        <c:set var="admin" value="admin"/>
        <c:if test="${userRole == admin}">
            <div align="right">
                <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                        onclick="openAddCarDialog()">
                    <fmt:message key="add.car"/>
                </button>
                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                        onclick="location.href='/car_list?cars=rented'">
                    <fmt:message key="rented"/>
                </button>
                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                        onclick="location.href='/car_list?cars=notRented'">
                    <fmt:message key="not.rented"/>
                </button>
            </div>
        </c:if>

        <div class="w3-container w3-light-blue">
            <h2><fmt:message key="car.list"/></h2>
        </div>



            <c:set var="notRentedCars" value="notRentedCars" />
            <c:if test="${not empty requestScope.get(notRentedCars)}">
                <ul class="w3-ul">
                    <c:forEach items="${requestScope.get(notRentedCars)}" var="car" >
                        <li class="w3-hover-sand">

                            <c:if test="${userRole == admin}"> ID ${car.getId()} --- </c:if>
                                ${car.getName()} ---
                                ${car.getDailyRentalPrice()}$ ---
                                ${(car.getCarClass()).name()}

                            <div align="right">
                                <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                        onclick="openRentCarDialog('${car.getId()}', '${car.getDailyRentalPrice()}')">
                                    <fmt:message key="rent"/>
                                </button>

                                <c:if test="${userRole == admin}">
                                <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                        onclick="openUpdateCarDialog('${car.getId()}', '${car.getName()}',
                                                '${car.getDailyRentalPrice()}', '${car.getCarClass()}')">
                                    <fmt:message key="update"/>
                                </button>
                                <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                        onclick="deleteCar(${car.getId()})">
                                    <fmt:message key="delete"/>
                                </button>
                                </c:if>

                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>

        <c:set var="rentedCars" value="rentedCars" />
        <c:if test="${not empty requestScope.get(rentedCars)}">
            <ul class="w3-ul">
                <c:forEach items="${requestScope.get(rentedCars)}" var="car" >
                    <li class="w3-hover-sand">
                        ID ${car.getId()} --- ${car.getName()} --- ${car.getDailyRentalPrice()}
                        $ --- ${(car.getCarClass()).name()}
                        <div align="right">
                            <!-- Here smth -->
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${empty requestScope.get(notRentedCars) and empty requestScope.get(rentedCars)}">
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right
                      w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×
                </span>
                <h5><fmt:message key="list.is.empty"/></h5>
            </div>
        </c:if>



        <!-- ----------- Add Car Popup Window ----------- -->
        <div id="add-car-dialog-overlay"></div>
        <div id="add-car-dialog-box">
            <a class="add-car-btn-close" onclick="closeAddCarDialog()">X</a>
            <div class="add-car-dialog-content">
                <!--------------------------------->
                <p align="center">
                    <span style="color: #333333; font-size: 22px; font-weight: 700;">
                        <fmt:message key="add.car"/>
                    </span>
                </p>


                <p align="center">
                    <label>
                        <input type="text" name="adding_carName"
                               style="width: 30%" id="adding_carName"
                               maxlength="50"
                               placeholder="<fmt:message key="name"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_car_name"></p>

                <p align="center">
                    <label>
                        <input type="number" name="adding_carDailyRentalPrice"
                               style="width: 30%" id="adding_carDailyRentalPrice"
                               maxlength="20"
                               placeholder="<fmt:message key="rental.price"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_car_price"></p>


                <p align="center">
                    <span>
                        <select id="adding_car_class_selection">
                            <option>MINI</option>
                            <option>ECONOMY</option>
                            <option>COMPACT</option>
                            <option>AVERAGE</option>
                            <option>STANDART</option>
                            <option>FAMILY</option>
                            <option>SPECIAL</option>
                            <option>PREMIUM</option>
                            <option>LUX</option>
                            <option>LARGESIZE</option>
                        </select>
                    </span>
                </p>


                <p align="center">
                    <label>
                        <input type="number" name="adding_amount_cars"
                               style="width: 30%" id="adding_amount_cars"
                               maxlength="20"
                               placeholder="<fmt:message key="amount"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_car_amount"></p>


                <p align="center">
                    <button name="add_car_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="add_car_btn" onclick="addCar()">
                        <fmt:message key="add.car"/>
                    </button>
                </p>

                <div id="add_car_loading" hidden>
                    <p align="center">
                        <img src="http://i.stack.imgur.com/FhHRx.gif" />
                        <fmt:message key="pls.wait"/>
                    </p>
                </div>

                <p align="center">
                    <fmt:message key="def.status.not.rented"/>
                </p>
                <!--------------------------------->
            </div>
        </div>
        <!-- ----------- Add Car Popup Window ----------- -->



        <!-- ----------- Update Car Popup Window ----------- -->
        <div id="update-car-dialog-overlay"></div>
        <div id="update-car-dialog-box">
            <a class="update-car-btn-close" onclick="closeUpdateCarDialog()">X</a>
            <div class="update-car-dialog-content">
                <!--------------------------------->
                <p align="center">
                    <span style="color: #333333; font-size: 22px; font-weight: 700;">
                        <fmt:message key="update.car"/>
                    </span>
                </p>


                <p align="center">
                    <label>
                        <input type="text" name="updating_carName"
                               style="width: 30%" id="updating_carName"
                               maxlength="50"
                               placeholder="<fmt:message key="name"/>">*
                    </label>
                </p>
                <p align="center" id="updating_incorrect_name"></p>

                <p align="center">
                    <label>
                        <input type="number" name="updating_carDailyRentalPrice"
                               style="width: 30%" id="updating_carDailyRentalPrice"
                               maxlength="20"
                               placeholder="<fmt:message key="rental.price"/>">*
                    </label>
                </p>
                <p align="center" id="updating_incorrect_price"></p>


                <p align="center">
                    <span>
                        <select id="updating_car_class_selection">
                            <option>MINI</option>
                            <option>ECONOMY</option>
                            <option>COMPACT</option>
                            <option>AVERAGE</option>
                            <option>STANDART</option>
                            <option>FAMILY</option>
                            <option>SPECIAL</option>
                            <option>PREMIUM</option>
                            <option>LUX</option>
                            <option>LARGESIZE</option>
                        </select>
                    </span>
                </p>

                <p align="center">
                    <button name="update_car_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="update_car_btn" onclick="updateCar(updatingCarId)">
                         <fmt:message key="update.car"/>
                    </button>
                </p>

                <div id="update_car_loading" hidden>
                    <p align="center">
                        <img src="http://i.stack.imgur.com/FhHRx.gif" />
                        <fmt:message key="pls.wait"/>
                    </p>
                </div>

                <p align="center">
                    <fmt:message key="def.status.not.rented"/>
                </p>
                <!--------------------------------->
            </div>
        </div>
        <!-- ----------- Update Car Popup Window ----------- -->



        <!-- ----------- Rent Car Popup Window ----------- -->
        <div id="rent-car-dialog-overlay"></div>
        <div id="rent-car-dialog-box">
            <a class="rent-car-btn-close" onclick="closeRentCarDialog()">X</a>
            <div class="rent-car-dialog-content">
                <!--------------------------------->
                <p align="center">
                    <span style="color: #333333; font-size: 22px; font-weight: 700;">
                        <fmt:message key="rent.car"/>
                    </span>
                </p>


                <p align="center">
                    <fmt:message key="receiving.date"/>:
                </p>

                <p align="center">*
                    <label>
                        <input type="text" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
                               name="receiving_date"
                               style="width: 30%" id="receiving_date"
                               placeholder="YYYY-MM-DD">*
                    </label>
                </p>
                <p align="center" id="incorrect_receiving_date"></p>


                <p align="center">
                    <fmt:message key="return.date"/>:
                </p>

                <p align="center">*
                    <label>
                        <input type="text" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
                               name="return_date"
                               style="width: 30%" id="return_date"
                               placeholder="YYYY-MM-DD">*
                    </label>
                </p>
                <p align="center" id="incorrect_return_date"></p>


                <p align="center">
                    <fmt:message key="passport.data"/>
                </p>

                <p align="center">*
                    <label>
                        <input type="text" name="renter_name"
                               style="width: 30%" id="renter_name"
                               maxlength="45"
                               placeholder="<fmt:message key="renter.name"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_renter_name"></p>


                <p align="center">*
                    <label>
                        <input type="text" name="renter_surname"
                               style="width: 30%" id="renter_surname"
                               maxlength="45"
                               placeholder="<fmt:message key="renter.surname"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_renter_surname"></p>


                <p align="center">
                    <fmt:message key="renter.birthday"/>
                </p>

                <p align="center">*
                    <label>
                        <input type="text" pattern="[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"
                               name="renter_birthday"
                               style="width: 30%" id="renter_birthday"
                               placeholder="YYYY-MM-DD">*
                    </label>
                </p>
                <p align="center" id="incorrect_renter_birthday"></p>


                <p align="center">*
                    <label>
                        <input type="text" name="renter_id_number"
                               style="width: 30%" id="renter_id_number"
                               maxlength="45"
                               placeholder="<fmt:message key="renter.id.number"/>">*
                    </label>
                </p>
                <p align="center" id="incorrect_renter_id_number"></p>


                <p align="center">
                    <button name="rent_car_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="rent_car_btn"
                            onclick="rentCar('${userEmail}', 0)"> <!-- 0 - Ad Service Price -->
                        <fmt:message key="send.request"/>
                    </button>
                </p>

                <div id="rent_car_loading" hidden>
                    <p align="center">
                        <img src="http://i.stack.imgur.com/FhHRx.gif" />
                        <fmt:message key="pls.wait"/>
                    </p>
                </div>


                <!--------------------------------->
            </div>
        </div>
        <!-- ----------- Rent Car Popup Window ----------- -->



    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">
        <fmt:message key="back"/>
    </button>
</div>


</body>
</html>
