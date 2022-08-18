<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form:form action="${pageContext.request.contextPath}/Admin/product-type.htm"
               method="post" modelAttribute="productType" class="form-horizontal">
        <form:input type="text" placeholder="Nhập maLoai" path="maLoai"/>
        <form:errors class="error" path="maLoai"/>

        <form:input path="tenLoai" class="form-control" placeholder="Enter tenLoai"
                    id="tenLoai"/>
        <form:errors path="tenLoai" cssClass="text-danger"/>
        
        <button name="${btnStatus}">Lưu</button>
    </form:form>
    <h2>DANH SÁCH LO?I SẢN PHẨM</h2>
    <jsp:useBean id="pagedListHolder" scope="request"
                 type="org.springframework.beans.support.PagedListHolder"/>
    <c:url value="Admin/product.htm" var="pagedLink">
        <c:param name="p" value="~"/>
    </c:url>
    <div>
        <tg:paging pagedListHolder="${pagedListHolder}"
                   pagedLink="${pagedLink}"/>

    </div>
    <table>
        <tr>
            <th>Mã lo?i</th>
            <th>Tên lo?i</th>
            <th>Action</th>
        </tr>
        <c:forEach var="s" items="${pagedListHolder.pageList}">
            <tr>
                <td>${s.maLoai}</td>
                <td>${s.tenLoai}</td>
                <td><a href="Admin/product-type/${s.maLoai}.htm?linkEdit">Sửa</a></td>
                <td>
                    <a href="Admin/product-type/${s.maLoai}.htm?linkDelete">Xóa</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    </body>
</html>
