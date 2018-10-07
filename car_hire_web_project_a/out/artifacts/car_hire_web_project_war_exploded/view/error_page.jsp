<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>

    <h1> Error </h1>

    Request from ${pageContext.errorData.requestURI} is failed
    <br/>
    Servlet name: ${pageContext.errorData.servletName}
    <br/>
    Status code: ${pageContext.errorData.statusCode}
    <br/>
    Exception: ${pageContext.exception}
    <br/>
    Message from exception: ${pageContext.exception.message}

    <button class="w3-btn w3-round-large" onclick="location.href='/'">
        <fmt:message key="back"/>
    </button>

</body>
</html>
