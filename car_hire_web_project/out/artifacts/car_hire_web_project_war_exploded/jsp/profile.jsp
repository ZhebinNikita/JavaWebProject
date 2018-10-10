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
    <title> <fmt:message key="site.title"/>  </title>

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

    <script type="text/javascript" src="../js/logout.js"></script>

</head>
<body class="w3-light-grey">
User: ${userEmail} Role: ${userRole}


<button class="w3-btn w3-hover-light-blue w3-round-large w3-margin-bottom w3-right-align" onclick="logout()">
    <fmt:message key="log.out"/>
</button>


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

This is my profile................................

    User: ${userEmail};
    Role: ${userRole};

</div>


</body>


</html>
