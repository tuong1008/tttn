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
        <title>JSP Page</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <%@include file="/WEB-INF/views/Admin/menu.jsp" %>
        <div class="container">
            <h2>THÔNG TIN LOẠI SẢN PHẨM</h2>
            <form:form action="${pageContext.request.contextPath}/Admin/product-type.htm"
                       method="post" modelAttribute="productType" class="form-horizontal">
                <form:input type="hidden" placeholder="Nhập maLoai" path="maLoai"/>
                <%--<form:errors class="error" path="maLoai"/>--%>

                <div class="form-element">
                    <label class="label-title" for="">Tên loại: </label>
                    <form:input path="tenLoai" class="form-control" placeholder="Nhập tên Loại"
                                id="tenLoai"/>
                    <form:errors path="tenLoai" cssClass="text-danger"/>    
                </div>

                <button name="${btnStatus}">Lưu</button>
            </form:form>
            <h2>DANH SÁCH LOẠI SẢN PHẨM</h2>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="Admin/product-type.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>

            </div>
            <div class="error">${message}</div>
            <table>
                <tr>
                    <th>Mã loại</th>
                    <th>Tên loại</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maLoai}</td>
                        <td>${s.tenLoai}</td>
                        <td>
                            <a href="Admin/product-type/${s.maLoai}.htm?linkEdit">Sửa</a>
                        </td>
                        <td>
                            <a href="Admin/product-type/${s.maLoai}.htm?linkDelete">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
