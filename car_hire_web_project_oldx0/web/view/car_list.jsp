<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<fmt:setBundle basename="lant"/>

<html>
<head>
    <title>Cars</title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <link href="../css/popup_add_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_add_car_window.js"></script>

    <link href="../css/popup_update_car_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_update_car_window.js"></script>

    <script type="text/javascript" src="../js/add_car.js"></script>
    <script type="text/javascript" src="../js/delete_car.js"></script>
    <script type="text/javascript" src="../js/update_car.js"></script>

</head>

<body class="w3-light-grey">
<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Car List</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">

        <div align="right">
            <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom" onclick="openAddCarDialog()">
                Add Car
            </button>
        </div>

        <div class="w3-container w3-light-blue">
            <h2>Cars to rent</h2>
        </div>



        <c:set var="notRentedCars" value="notRentedCars" />


        <c:if test="${requestScope.get(notRentedCars) != null && not empty requestScope.get(notRentedCars)}">
            <ul class="w3-ul">
            <c:forEach items="${requestScope.get(notRentedCars)}" var="car" >
                <li class="w3-hover-sand">
                        ID ${car.getId()} --- ${car.getName()} --- ${car.getDailyRentalPrice()}
                    $ --- ${(car.getCarClass()).name()}
                    <div align="right">
                        <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                onclick="">
                            Order
                        </button>
                        <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                onclick="openUpdateCarDialog('${car.getId()}', '${car.getName()}',
                                        '${car.getDailyRentalPrice()}', '${car.getCarClass()}')">
                            Update
                        </button>
                        <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom"
                                onclick="deleteCar(${car.getId()})">
                            Delete
                        </button>
                    </div>
                </li>
            </c:forEach>
            </ul>
        </c:if>

        <c:if test="${requestScope.get(notRentedCars) == null || empty requestScope.get(notRentedCars)}">
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right
                      w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×
                </span>
                <h5>List is empty!</h5>
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
                        Add Car
                    </span>
                </p>


                <p align="center">
                    <label>
                        <input type="text" name="adding_carName"
                               style="width: 30%" id="adding_carName"
                               maxlength="50" placeholder="Name">
                    </label>
                </p>
                <p align="center" style='color:red;' id="incorrect_car_name"></p>

                <p align="center">
                    <label>
                        <input type="number" name="adding_carDailyRentalPrice"
                               style="width: 30%" id="adding_carDailyRentalPrice"
                               maxlength="20" placeholder="Rental price USD/day">
                    </label>
                </p>
                <p align="center" style='color:red;' id="incorrect_car_price"></p>


                <p align="center">
                    <span>
                        <select id="adding_car_class_selection">
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
                    <label>
                        <input type="number" name="adding_amount_cars"
                               style="width: 30%" id="adding_amount_cars"
                               maxlength="20" placeholder="Amount">
                    </label>
                </p>
                <p align="center" style='color:red;' id="incorrect_car_amount"></p>


                <p align="center">
                    <button name="add_car_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="add_car_btn" onclick="addCar()">
                        Add Car
                    </button>
                </p>

                <div id="add_car_loading" hidden>
                    <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />   Please wait...</p>
                </div>

                <p align="center"> Default status - is not rented.</p>
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
                        Update Car
                    </span>
                </p>


                <p align="center">
                    <label>
                        <input type="text" name="updating_carName"
                               style="width: 30%" id="updating_carName"
                               maxlength="50" placeholder="Name">
                    </label>
                </p>
                <p align="center" style='color:red;' id="updating_incorrect_name"></p>

                <p align="center">
                    <label>
                        <input type="number" name="updating_carDailyRentalPrice"
                               style="width: 30%" id="updating_carDailyRentalPrice"
                               maxlength="20" placeholder="Rental price (USD/day)">
                    </label>
                </p>
                <p align="center" style='color:red;' id="updating_incorrect_price"></p>


                <p align="center">
                    <span>
                        <select id="updating_car_class_selection">
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
                    <button name="update_car_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                            id="update_car_btn" onclick="updateCar(updatingCarId)">
                        Update Car
                    </button>
                </p>

                <div id="update_car_loading" hidden>
                    <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />   Please wait...</p>
                </div>

                <p align="center"> Default status - is not rented.</p>
                <!--------------------------------->
            </div>
        </div>
        <!-- ----------- Update Car Popup Window ----------- -->


    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back</button>
</div>


</body>
</html>
