<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<c:url value='/resource/css/Admin/menuStyle.css' />"
              rel="stylesheet">
        <link href="<c:url value='/resource/css/Admin/adminStyle.css' />"
              rel="stylesheet">
        <link href="<c:url value='/resource/css/Style.css' />" rel="stylesheet">
        <link
            href="<c:url value='/resource/fontawesome-free-5.15.4-web/css/all.css' />"
            rel="stylesheet">
        <title>Insert title here</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <%@include file="/WEB-INF/views/Admin/menu.jsp" %>
        <div class="container">
            <h2>SỬA KHUYẾN MÃI</h2>
            <form action="${pageContext.request.contextPath}/Admin/promotion/${maKM}.htm"
                  method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="form-element">
                        <label>Ngày bắt đầu</label>
                        <input type="date" name="ngayBD" value="${ngayBD}"/>    
                    </div>
                    <div class="form-element">
                        <label>Ngày kết thúc</label>
                        <input type="date" name="ngayKT" value="${ngayKT}"/>    
                    </div>
                    <div>
                        <label>Mô tả</label>
                        <textarea style="width: 100%" cols="20" rows="10" name="moTa">${moTa}</textarea>    
                    </div>
                </div>
                <button name="${btnStatus}">Lưu</button>
            </form>
    </body>
</html>
