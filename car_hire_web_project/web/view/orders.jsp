<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

</head>
<body class="w3-light-grey">


<div class="w3-container w3-blue-grey w3-opacity w3-right-align">
    <h1>Car List</h1>
</div>

<div class="w3-container w3-center w3-margin-bottom w3-padding">
    <div class="w3-card-4">

        <div align="right">
            <button class="w3-btn w3-hover-green w3-round-large w3-margin-bottom" onclick="">
                some useless button
            </button>
        </div>

        <div class="w3-container w3-light-blue">
            <h2>Orders</h2>
        </div>


        <c:set var="orders" value="orders" />


        <c:if test="${requestScope.get(orders) != null && not empty requestScope.get(orders)}">
            <ul class="w3-ul">
                <c:forEach items="${requestScope.get(orders)}" var="order" >
                    <li class="w3-hover-sand">
                        ID ${order.getId()} --- ${order.getUserName()} --- Car ID: ${order.getCarId()} ---
                        Receiving Date: --- ${(order.getReceivingDate())} --- Return Date: ${order.getReturnDate()}
                        --- Rental Price: ${order.getRentalPrice()} USD --- AdService Price: ${order.getAdServicePrice()}
                        --- Order is paid: ${order.getOrderIsPaid()}
                        --- Ad Info: ${order.getAdInfo()}

                        <div align="right">
                            <!--buttons here...-->
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${requestScope.get(orders) == null || empty requestScope.get(orders)}">
            <div class="w3-panel w3-red w3-display-container w3-card-4 w3-round">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-margin-right w3-display-right
                      w3-round-large w3-hover-red w3-border w3-border-red
                      w3-hover-border-grey">×
                </span>
                <h5>List is empty!</h5>
            </div>
        </c:if>


    </div>
</div>

<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='/'">Back</button>
</div>


</body>
</html>
