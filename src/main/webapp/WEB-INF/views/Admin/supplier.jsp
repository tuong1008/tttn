<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
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
            <h2>THÔNG TIN NHÀ CUNG CẤP</h2>
            <div class="error">${message}</div>
            <form:form action="${pageContext.request.contextPath}/Admin/supplier.htm"
                       method="post" modelAttribute="supplier" class="form-horizontal">
                <div class="form-group">
                    <form:input type="hidden" placeholder="Nhập mã" path="maNCC"/> 


                    <div class="form-element">
                        <label class="label-title" for="">Tên: </label>
                        <form:input path="tenNCC" class="form-control" placeholder="Nhập tên nhà cung cấp"
                                    id="tenNCC"/>
                        <form:errors path="tenNCC" cssClass="text-danger"/>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Số điện thoại: </label>
                        <form:input path="sdt" class="form-control" placeholder="Nhập số điện thoại"
                                    id="sdt"/>
                        <form:errors path="sdt" cssClass="text-danger"/>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Địa chỉ: </label>
                        <form:input path="diaChi" class="form-control" placeholder="Nhập địa chỉ"
                                    id="diaChi"/>
                        <form:errors path="diaChi" cssClass="text-danger"/>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Email: </label>
                        <form:input path="email" class="form-control" placeholder="Nhập email"
                                    id="email"/>
                        <form:errors path="email" cssClass="text-danger"/>    
                    </div>
                </div>
                <button name="${btnStatus}">Lưu</button>
            </form:form>
            <h2>DANH SÁCH NHÀ CUNG CẤP</h2>
            <div class="error">${message}</div>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="Admin/supplier.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>

            </div>
            <table>
                <tr>
                    <th>Mã nhà cung cấp</th>
                    <th>Tên nhà cung cấp</th>
                    <th>Số điện thoại</th>
                    <th>Địa chỉ</th>
                    <th>Email</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maNCC}</td>
                        <td>${s.tenNCC}</td>
                        <td>${s.sdt}</td>
                        <td>${s.diaChi}</td>
                        <td>${s.email}</td>
                        <td><a href="Admin/supplier/${s.maNCC}.htm?linkEdit">Sửa</a></td>
                        <td>
                            <a href="Admin/supplier/${s.maNCC}.htm?linkDelete">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </body>
</html>
