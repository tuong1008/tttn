<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/resource/css/Admin/menuStyle.css' />"
          rel="stylesheet">
    <link href="<c:url value='/resource/css/Style.css' />" rel="stylesheet">
    <link
            href="<c:url value='/resource/fontawesome-free-5.15.4-web/css/all.css' />"
            rel="stylesheet">


    <style type="text/css">
        .info-app {
            padding: 24px;
            display: flex;
            align-items: right;
            margin: 100px 100px;
            margin-bottom: 100px;
            margin-left: 350px;
            padding-top: 12px;
            position: relative;
        }

        .infos {
            margin: 24px 0;
        }

        .info-login {
            transform: translate(+5%, 0);
            text-align: center;
        }

        .info-login img {
            width: 50%;
            display: block;
            margin-bottom: 12px;
        }

        .info-login p {
            color: #007bff;
            font-size: 20px;
            cursor: pointer;
        }

        .info-login a:hover {
            color: blue;
        }

        .info-user {
            transform: translate(+10%, 0);
            /* border: solid 1px; */
            padding: 24px;
            border-radius: 5px;
            box-shadow: 0 0 4px 4px rgb(134, 131, 131);
        }

        .info__btn, .pass__btn {
            display: flex;
            margin-top: 24px;
        }

        label {
            display: inline-block;
            width: 120px;
        }

        .info,
        input {
            font-size: 20px;
            width: 300px;
            padding: 4px;
            border: solid 1px;
        }

        .item,
        .form__item {
            margin-bottom: 12px;
            font-size: 20px;
        }

        button, .a-btn {
            padding: 8px 24px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            border: none;
            font-size: 20px;
        }
    </style>
    <title>Insert title here</title>
    <base href="${pageContext.servletContext.contextPath}/">
</head>
<body>
<%@include file="/WEB-INF/views/Admin/menu.jsp" %>
<div class="info-app">
    <div class="info-login">
        <img src="resource/img/USER.png" alt="">
        <a class="a-btn" href="Admin/changePass.htm">Thay ?????i m???t kh???u</a>
    </div>
    <div class="info-user">
        <h1>TH??NG TIN C?? NH??N</h1>
        <div class="infos">
            <div class="item">
                <label for="">M?? nh??n vi??n</label>
                <label class="info">${sessionScope.staff.userId}</label>
            </div>
            <div class="item">
                <label for="">H??? v?? t??n</label>
                <label class="info">${sessionScope.staff.hoTen}</label>
            </div>
            <div class="item">
                <label for="">Gi???i t??nh</label>
                <label class="info">${sessionScope.staff.gioiTinh}</label>
            </div>
            <div class="item">
                <label for="">S??? ??i???n tho???i</label>
                <label class="info">${sessionScope.staff.sdt}</label>
            </div>
            <div class="item">
                <label for="">Email</label>
                <label class="info">${sessionScope.staff.email}</label>
            </div>
            <div class="item">
                <label for="">?????a ch???</label>
                <label class="info">${sessionScope.staff.diaChi}</label>
            </div>
        </div>
    </div>

</div>
</body>
</html>