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
    <title> <fmt:message key="orders"/> </title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <script type="text/javascript" src="../js/order/accept_order.js"></script>

    <link href="../css/popup_deny_order_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_deny_order_window.js"></script>
    <script type="text/javascript" src="../js/order/deny_order.js"></script>

    <link href="../css/popup_add_order_info_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_add_order_info_window.js"></script>
    <script type="text/javascript" src="../js/order/add_order_info.js"></script>

    <link href="../css/popup_add_ad_service_price_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_add_ad_service_price_window.js"></script>
    <script type="text/javascript" src="../js/order/add_ad_service_price.js"></script>

    <script type="text/javascript" src="../js/order/delete_order.js"></script>

</head>
<body class="w3-light-grey">
User: ${userEmail} Role: ${userRole}

<div class="w3-container w3-blue-grey w3-opacity">

    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru_RU" value="en" ${language == 'ru_RU' ? 'selected' : ''}>Russian</option>
        </select>
    </form>

    <h1> <fmt:message key="orders"/> </h1>

</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">

        <div align="right">
            <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    onclick="location.href='/orders?orders=notPaid'">
                <fmt:message key="applications"/>
            </button>
            <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    onclick="location.href='/orders?orders=paid'">
                <fmt:message key="registered.orders"/>
            </button>
        </div>



        <c:set var="notPaidOrders" value="notPaidOrders" />
        <c:if test="${not empty requestScope.get(notPaidOrders)}">
            <ul class="w3-ul">
                <c:forEach items="${requestScope.get(notPaidOrders)}" var="order" >
                    <li class="w3-hover-sand">
                        <div>
                            <div align="left">
                                <div>ID ${order.getId()}: ${order.getUserName()};</div>
                                <div>Car ID: ${order.getCarId()};</div>
                                <div>Receiving Date: ${(order.getReceivingDate())} - Return Date: ${order.getReturnDate()};</div>
                                <div>Rental Price: ${order.getRentalPrice()} USD;</div>
                                <div>AdService Price: ${order.getAdServicePrice()};</div>
                                <div>Order is paid: ${order.getOrderIsPaid()};</div>
                                <div>Ad Info: ${order.getAdInfo()}</div>
                            </div>

                            <div align="right">
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="acceptOrder('${order.getId()}', '${order.getUserName()}',
                                                '${order.getRentalPrice()}', '${order.getAdServicePrice()}')">
                                    <fmt:message key="accept"/>
                                </button>
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="openDenyOrderDialog('${order.getId()}')">
                                    <fmt:message key="deny"/>
                                </button>
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="deleteOrder('${order.getId()}')">
                                    <fmt:message key="delete"/>
                                </button>
                            </div>
                            <p align="center" id="order_response"></p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:set var="paidOrders" value="paidOrders" />
        <c:if test="${not empty requestScope.get(paidOrders)}">
            <ul class="w3-ul">
                <c:forEach items="${requestScope.get(paidOrders)}" var="order" >
                    <li class="w3-hover-sand">
                        <div>
                            <div align="left">
                                <div>ID ${order.getId()}: ${order.getUserName()};</div>
                                <div>Car ID: ${order.getCarId()};</div>
                                <div>Receiving Date: ${(order.getReceivingDate())} - Return Date: ${order.getReturnDate()};</div>
                                <div>Rental Price: ${order.getRentalPrice()} USD;</div>
                                <div>AdService Price: ${order.getAdServicePrice()};</div>
                                <div>Order is paid: ${order.getOrderIsPaid()};</div>
                                <div>Ad Info: ${order.getAdInfo()}</div>
                            </div>


                            <div align="right">
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="openAddOrderInfoDialog('${order.getId()}')">
                                    <fmt:message key="add.info"/>
                                </button>
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="openAddAdServicePriceDialog('${order.getId()}')">
                                    <fmt:message key="add.ad.service.price"/>
                                </button>
                                <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                                        onclick="deleteOrder('${order.getId()}')">
                                    <fmt:message key="delete"/>
                                </button>
                            </div>
                            <p align="center" id="paid_order_response"></p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${empty requestScope.get(notPaidOrders) and empty requestScope.get(paidOrders)}">
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right
                      w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×
                </span>
                <h5>
                    <fmt:message key="list.is.empty"/>
                </h5>
            </div>
        </c:if>


    </div>
</div>




<!-- ---------- Popup Deny Order Window ---------- -->
<div id="deny-order-dialog-overlay"></div>
<div id="deny-order-dialog-box">
    <a class="deny-order-btn-close" onclick="closeDenyOrderDialog()">X</a>
    <div class="deny-order-dialog-content">
        <!--------------------------------->

        <p align="center">
            <label>
                <input type="text" name="ad_info"
                       style="width: 30%" id="ad_info"
                       placeholder="">
            </label>
        </p>
        <p align="center" id="incorrect_deny_order"></p>


        <p align="center">
            <button name="deny_order_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="deny_order_btn" onclick="denyOrder()">
                <fmt:message key="deny"/>
            </button>
        </p>

        <div id="deny_order_loading" hidden>
            <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />
                <fmt:message key="pls.wait"/>
            </p>
        </div>
        <!--------------------------------->
    </div>
</div>
<!-- ---------- Popup Deny Order Window ---------- -->



<!-- ---------- Popup Add Order Info Window ---------- -->
<div id="add-order-info-dialog-overlay"></div>
<div id="add-order-info-dialog-box">
    <a class="add-order-info-btn-close" onclick="closeAddOrderInfoDialog()">X</a>
    <div class="add-order-info-dialog-content">
        <!--------------------------------->

        <p align="center">
            <label>
                <input type="text" name="order_info"
                       style="width: 30%" id="order_info"
                       placeholder="">
            </label>
        </p>
        <p align="center" id="incorrect_add_order_info"></p>


        <p align="center">
            <button name="add_order_info_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="add_order_info_btn" onclick="addOrderInfo()">
                <fmt:message key="add.info"/>
            </button>
        </p>

        <div id="add_order_info_loading" hidden>
            <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />
                <fmt:message key="pls.wait"/>
            </p>
        </div>
        <!--------------------------------->
    </div>
</div>
<!-- ---------- Popup Add Order Info Window ---------- -->



<!-- ---------- Popup Add AdService Price Window ---------- -->
<div id="add-ad-service-price-dialog-overlay"></div>
<div id="add-ad-service-price-dialog-box">
    <a class="add-ad-service-price-btn-close" onclick="closeAddAdServicePriceDialog()">X</a>
    <div class="add-ad-service-price-dialog-content">
        <!--------------------------------->

        <p align="center">
            <label>
                <input type="number" name="ad_service_price"
                       style="width: 30%" id="ad_service_price"
                       placeholder="">
            </label>
        </p>
        <p align="center" id="incorrect_ad_service_price"></p>


        <p align="center">
            <button name="ad_service_price_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="ad_service_price_btn" onclick="addAdServicePrice()">
                <fmt:message key="add"/>
            </button>
        </p>

        <div id="add_ad_service_price_loading" hidden>
            <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />
                <fmt:message key="pls.wait"/>
            </p>
        </div>
        <!--------------------------------->
    </div>
</div>
<!-- ---------- Popup Add AdService Price Window ---------- -->




<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">
        <fmt:message key="back"/>
    </button>
</div>


</body>
</html>
