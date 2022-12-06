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
            <h2>THÔNG TIN SẢN PHẨM</h2>
            <form:form action="${pageContext.request.contextPath}/Admin/product.htm"
                       enctype="multipart/form-data" method="post" modelAttribute="product" class="form-horizontal">
                <div class="form-group">
                    <form:input type="hidden" path="maSP"/>
                    <div class="form-element">
                        <label class="label-title" for="">Tên sản phẩm: </label>
                        <form:input path="tenSP" class="form-control" placeholder="Nhập tên sản phẩm"
                                    id="tenSP"/>
                        <form:errors path="tenSP" cssClass="text-danger"/>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Mô tả: </label>
                        <form:input path="moTa" class="form-control" placeholder="Nhập mô tả"
                                    id="moTa"/>
                        <form:errors path="moTa" cssClass="text-danger"/>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Hình ảnh: </label>
                        <input onchange="chooseImgEvent(event)" type="file" name="hinhAnh" class="form-control"  accept="image/*"/>    
                        <c:if test="${not empty product.hinhAnh}">
                            <img id="productImg" style="max-width: 100px" src="resource/img/imgProduct/${product.hinhAnh}">
                        </c:if>
                        <c:if test="${empty product.hinhAnh}">
                            <img id="productImg" style="max-width: 100px">
                        </c:if>
                    </div>
                    <%--<form:input path="gia" class="form-control" placeholder="Enter gia"--%>
                    <!--id="gia"/>-->
                    <%--<form:input path="slt" class="form-control" placeholder="Enter slt"--%>
                    <!--id="slt"/>-->

                    <div class="form-element">
                        <label class="label-title" for="">Loại sản phẩm:</label>
                        <form:select path="loaiSP.maLoai">
                            <form:options itemValue="maLoai" itemLabel="tenLoai" items="${categories}"/>
                        </form:select>    
                    </div>

                    <div class="form-element">
                        <label class="label-title" for="">Nhà cung cấp:</label>
                        <form:select path="nhaCungCap.maNCC">
                            <form:options itemValue="maNCC" itemLabel="tenNCC" items="${suppliers}"/>
                        </form:select>    
                    </div>
                </div>
                <button name="${btnStatus}">Lưu</button>
            </form:form>
            <h2>DANH SÁCH SẢN PHẨM</h2>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="Admin/productList.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>

            </div>
            <div class="error">${message}</div>
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
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maSP}</td>
                        <td>${s.tenSP}</td>
                        <td>${s.moTa}</td>
                        <td><img style="max-width: 100px" src="resource/img/imgProduct/${s.hinhAnh}"></td>
                        <td>${s.gia}</td>
                        <td>${s.slt}</td>
                        <td>${s.spMoi}</td>
                        <td>${s.loaiSP.tenLoai}</td>
                        <td>${s.nhaCungCap.maNCC}</td>
                        <td><a href="Admin/product/${s.maSP}.htm?linkEdit">Sửa</a></td>
                        <td>
                            <a href="Admin/product/${s.maSP}.htm?linkDelete">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <script>
            function chooseImgEvent(event) {
                const img = event.target.files[0];
                if (img) {
                    document.getElementById("productImg").src = URL.createObjectURL(img);
                }
            }
            ;
        </script>
    </body>
</html>
