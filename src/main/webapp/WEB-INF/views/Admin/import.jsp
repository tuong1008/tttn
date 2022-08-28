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
        <link href="<c:url value='/resource/css/Admin/adminStyle.css' />"
              rel="stylesheet">
        <link href="<c:url value='/resource/css/Style.css' />"
              rel="stylesheet">
        <link href="<c:url value='/resource/fontawesome-free-5.15.4-web/css/all.css' />"
              rel="stylesheet">
        <title>Insert title here</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <%@include file="/WEB-INF/views/Admin/menu.jsp"%>
        <div class="container">
            <h2>THÊM PHIẾU NHẬP</h2>
            <div class="message" style="text-align: center; color: red;">${message}</div>
            <form action="${pageContext.request.contextPath}/Admin/import/${maPD}.htm"
                  method="post" class="form-horizontal">
                <div class="form-group">
                    <c:forEach var = "i" items="${ctPhieuDat}">
                        <div>
                            <label>${i.pk.sanPham.tenSP}</label>
                            <input name="maSP" value="${i.pk.sanPham.maSP}" type="hidden"/>
                            <input name="gia" value="${i.gia}" readonly="true"/>
                            <input style = "width: 50px;margin: 10px 20px 10px 5px;" name="sl" value="${i.sl}" min="0" max="${i.sl}" type="number"/>
                        </div>
                    </c:forEach>
                </div>
                <br>
                <button name="${btnStatus}">Lưu</button>
            </form>
        </div>


    </body>
</html>
