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
    <form:form action="${pageContext.request.contextPath}/Admin/product.htm"
               enctype="multipart/form-data" method="post" modelAttribute="product" class="form-horizontal">
        <form:input type="text" placeholder="Nhập mã" path="maSP"/>
        <form:errors class="error" path="maSP"/>

        <form:input path="tenSP" class="form-control" placeholder="Enter tenSP"
                    id="tenSP"/>
        <form:errors path="tenSP" cssClass="text-danger"/>
        <form:input path="moTa" class="form-control" placeholder="Enter moTa"
                    id="moTa"/>
        <form:errors path="moTa" cssClass="text-danger"/>
        <form:input type="file" path="hinhAnh" class="form-control" placeholder="Enter hinhAnh"
                    id="hinhAnh"/>
        <form:errors path="hinhAnh" cssClass="text-danger"/>
        <form:input path="gia" class="form-control" placeholder="Enter gia"
                    id="gia"/>
        <form:errors path="gia" cssClass="text-danger"/>
        <form:input path="slt" class="form-control" placeholder="Enter slt"
                    id="slt"/>
        <form:errors path="slt" cssClass="text-danger"/>

        <form:select path="loaiSP">
            <form:options items="${categories}"/>
        </form:select>
        <form:errors path="loaiSP" cssClass="text-danger"/>
        
        <form:select path="nhaCungCap">
            <form:options items="${suppliers}"/>
        </form:select>
        <form:errors path="productCategory" cssClass="text-danger"/>
        <button name="${btnStatus}">Lưu</button>
    </form:form>
    <h2>DANH SÁCH SẢN PHẨM</h2>
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
            <th>Mã sản phẩm</th>
            <th>Tên sản phẩm</th>
            <th>Mô tả</th>
            <th>Hình ảnh</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Sản phẩm mới</th>
            <th>Loại sản phẩm</th>
            <th>Nhà cung cấp</th>
            <th>Action</th>
        </tr>
        <c:forEach var="s" items="${pagedListHolder.pageList}">
            <tr>
                <td>${s.maSP}</td>
                <td>${s.tenSP}</td>
                <td>${s.moTa}</td>
                <td>${s.hinhAnh}</td>
                <td>${s.gia}</td>
                <td>${s.slt}</td>
                <td>${s.spMoi}</td>
                <td>${s.loaiSP.tenLoai}</td>
                <td>${s.nhaCungCap.maNCC}</td>
                <td>
                    <a href="Admin/supplier/${s.maNCC}.htm?linkDelete">Xóa</a>
                </td>
                <td><a href="Admin/supplier/${s.maNCC}.htm?linkEdit">Sửa</a></td>
                <td><a href="Admin/supplier/${s.maNCC}.htm?linkReset">Reset</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
