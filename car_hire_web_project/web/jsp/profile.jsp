<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<c:set var="l" value="language"/>
<c:set var="language"
       value="${sessionScope.get(l)}"
       scope="session"/>
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
    <title> <fmt:message key="site.title"/>  </title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <script type="text/javascript" src="../js/logout.js"></script>

    <link href="../css/popup_update_balance_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_update_balance_window.js"></script>

    <script type="text/javascript" src="../js/update_balance.js"></script>

</head>
<body class="w3-light-grey">
User: ${userEmail} Role: ${userRole}


<button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="logout()">
    <fmt:message key="log.out"/>
</button>
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

    <h1> <fmt:message key="my.profile"/> </h1>

</div>


<div class="w3-container w3-center">

    <div align="left">
        User: ${userEmail};
        Role: ${userRole};
    </div>


    <div align="left">
        <button class="w3-btn w3-green w3-round-large w3-margin-bottom"
                onclick="openUpdateBalanceDialog()">
            <fmt:message key="top.up.balance"/>
        </button>
    </div>


    <c:set var="admin" value="admin"/>
    <c:if test="${userRole == admin}">
        <div align="left">
            <button name="to_orders" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="to_orders"
                    onclick="location.href='/orders'">
                <fmt:message key="orders"/>
            </button>
        </div>
    </c:if>


    <!-- ---------- Popup Update Balance Window ---------- -->
    <div id="update-balance-dialog-overlay"></div>
    <div id="update-balance-dialog-box">
        <a class="update-balance-btn-close" onclick="closeUpdateBalanceDialog()">X</a>
        <div class="update-balance-dialog-content">
            <!--------------------------------->
            <p align="center">
                <span style="color: #333333; font-size: 22px; font-weight: 700;">
                    <fmt:message key="top.up.balance"/>
                </span>
            </p>


            <p align="center">
                <label>
                    <input type="number" name="amount_of_money"
                           style="width: 30%" id="amount_of_money" maxlength="20"
                           placeholder="<fmt:message key="enter.amount"/>">
                </label>
            </p>
            <p align="center" style='color:red;' id="incorrect_amount_of_money"></p>


            <p align="center">
                <button name="update_balance_btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                        id="update_balance_btn" onclick="updateBalance('${userEmail}')">
                    <fmt:message key="top.up.balance"/>
                </button>
            </p>

            <div id="update_balance_loading" hidden>
                <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />
                    <fmt:message key="pls.wait"/>
                </p>
            </div>
            <!--------------------------------->
        </div>
    </div>
    <!-- ---------- Popup Update Balance Window ---------- -->



</div>


</body>


</html>
