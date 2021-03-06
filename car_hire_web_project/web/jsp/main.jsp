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

    <link href="../css/popup_login_window.css" rel="stylesheet" type="text/css" media="all">
    <script type="text/javascript" src="../js/popup/popup_login_window.js"></script>

    <script type="text/javascript" src="../js/login.js"></script>
    <script type="text/javascript" src="../js/logout.js"></script>

</head>

<body class="w3-light-grey">
User: ${userEmail} Role: ${userRole}
<c:if test="${not empty userEmail}">
    <button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="logout()">
        <fmt:message key="log.out"/>
    </button>
    <span>
        <fmt:message key="balance"/> ${accountBalance}$
    </span>
</c:if>

<div class="w3-container w3-blue-grey w3-opacity">

    <form>
        <select id="language" name="language" onchange="submit()">
            <option value="en" value="en" ${language == 'en' ? 'selected' : ''}>English</option>
            <option value="ru_RU" value="en" ${language == 'ru_RU' ? 'selected' : ''}>Russian</option>
        </select>
    </form>

    <h1> <fmt:message key="site.title"/> </h1>

</div>

<div class="w3-container w3-center">
    <div class="w3-bar w3-padding-large w3-padding-24">

        <c:if test="${not empty userEmail}">
            <button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="location.href='/car_list'">
                <fmt:message key="cars.list"/>
            </button>

            <button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="location.href='/profile'">
                <fmt:message key="my.profile"/>
            </button>
        </c:if>
        <c:if test="${empty userEmail}">
            <button class="w3-btn w3-green w3-round-large w3-margin-bottom" onclick="openLoginDialog()">
                <fmt:message key="login.or.register"/>
            </button>
        </c:if>

    </div>
</div>


<!-- ---------- Popup Login Window ---------- -->
<div id="login-dialog-overlay"></div>
<div id="login-dialog-box">
    <a class="login-btn-close" onclick="closeLoginDialog()">X</a>
    <div class="login-dialog-content">
        <!--------------------------------->
        <p align="center">
    <span style="color: #333333; font-size: 22px; font-weight: 700;">
        <fmt:message key="login.or.register"/>
    </span></p>


        <p align="center">
            <label>
                <input type="email" name="userEmail"
                       style="width: 30%" id="userEmail" maxlength="40"
                       placeholder=<fmt:message key="email"/>>
            </label>
        </p>
        <p align="center" id="incorrect_text_email"></p>

        <p align="center">
            <label>
                <input type="password" name="userPass"
                       style="width: 30%" id="userPass" maxlength="40"
                       placeholder=<fmt:message key="password"/>>
            </label>
        </p>
        <p align="center" id="incorrect_text_pass"></p>


        <p align="center">
            <button name="btn" class="w3-btn w3-green w3-round-large w3-margin-bottom"
                    id="login_btn" onclick="clickLogin()">
                <fmt:message key="login.or.register"/>
            </button>
        </p>

        <div id="loading" hidden>
            <p align="center"><img src="http://i.stack.imgur.com/FhHRx.gif" />
                <fmt:message key="pls.wait"/>
            </p>
        </div>
        <!--------------------------------->
    </div>
</div>
<!-- ---------- Popup Login Window ---------- -->


</body>


</html>
