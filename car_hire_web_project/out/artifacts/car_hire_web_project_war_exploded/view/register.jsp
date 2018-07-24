
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Прокат Автомобилей</title>
</head>
<body bgcolor="#f0f8ff">



    <hr>
    <p align="center"><font color="#003399"><font face="Courier New, Courier, monospace"><font size="6">
        <b>Прокат Автомобилей</b></font></font></font></p>
    <hr>

    <p align="center">
    <span style="color: #333333; font-size: 22px; font-weight: 700;">
        Войти или зарегистрироваться</span></p>



    <!--
    Здесь у формы указан атрибут method со значением post.
    Это говорит о том, что данные из этой формы полетят на сервер в виде POST-запроса.
    Атрибут action не указан, значит запрос отправится по тому же адресу, по которому мы перешли на эту страничку ("/").
    Таким образом, наш сервлет, привязанный к этому адресу, при получении GET-запроса возвращает эту jsp
    с формой добавления пользователей, а если получит POST-запрос, значит, форма отправила туда свои данные
    (которые мы в методе doPost() вытащим из объекта запроса, обработаем и передадим в модель на сохранение
    этих данных в Oracle DataBase).
    -->
    <form method="post">

    <p align="center">
        Логин:&nbsp;<br>
        <span style="color: #333333; font-size: 22px; font-weight: 700;">
      <input maxlength="100" name="userName" size="20" type="text">
        </span><br>
        Пароль:&nbsp;<br>
        <input maxlength="100" name="userPass" size="20" type="password">
    </p>

    <p align="center">
        <input type="button">  Войти / Зарегистрироваться  </input> <!-- onclick="location.href='/car_list'" -->
    </p>

    </form>



</body>
</html>
