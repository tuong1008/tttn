<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags"%>
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
            <h2>CHI TIẾT KHUYẾN MÃI</h2>
            <form:form action="${pageContext.request.contextPath}/Admin/promotionDetail/${maKM}.htm"
                       method="post" modelAttribute="ct" class="form-horizontal">
                <div class="form-group">
                    <form:input type="hidden" path="pk.khuyenMai.maKM"></form:input>
                    <div class="form-element">
                        <label class="label-title" for="">Tên sản phẩm: </label>
                    <c:if test="${btnStatus == 'btnAdd'}">
                        <form:select path="pk.sanPham.maSP">
                            <form:options itemValue="maSP" itemLabel="tenSP" items="${dsSanPham}"/>
                        </form:select>    
                    </c:if>
                    <c:if test="${btnStatus == 'btnEdit'}">
                        <input type="text" value="${ct.pk.sanPham.tenSP}" readonly="true"></input>
                        <form:input type="hidden" path="pk.sanPham.maSP"></form:input>    
                    </c:if>
                        
                        
                    </div>
                    <div class="form-element">
                        <label class="label-title" for="">% Giảm giá:</label>
                        <form:input path="giamGia" type="number" min="0" max="99"></form:input>    
                    </div>
                </div>
                <button name="${btnStatus}">Lưu</button>
            </form:form>
            
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
            <c:url value="Admin/promotionDetail/${s.pk.khuyenMai.maKM}.htm" var="pagedLink">
                <c:param name="p" value="tuong" />
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}" />
            </div>
            <table>
                <tr>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>% Giảm giá</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.pk.sanPham.maSP}</td>
                        <td>${s.pk.sanPham.tenSP}</td>
                        <td>${s.giamGia}</td>
                        <td><a href="Admin/promotionDetail.htm?maKM=${s.pk.khuyenMai.maKM}&maSP=${s.pk.sanPham.maSP}&linkEdit">Sửa</a></td>
                        <td>
                            <a href="Admin/promotionDetail.htm?maKM=${s.pk.khuyenMai.maKM}&maSP=${s.pk.sanPham.maSP}&linkDelete">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            ${message}
        </div>
    </body>
</html>