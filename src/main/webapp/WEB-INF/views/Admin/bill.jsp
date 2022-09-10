<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
        <fmt:setLocale value="vi_VN" scope="session"/>
        <div class="container">
            <h2>DANH SÁCH ĐƠN HÀNG
                <c:if test="${nameBill == 'billComplete'}">HOÀN THÀNH</c:if> 
                <c:if test="${nameBill == 'billDelivery'}">ĐANG GIAO</c:if> 
                <c:if test="${nameBill == 'billCancel'}">ĐÃ HỦY</c:if> 
                <c:if test="${nameBill == 'billUnConfirm'}">CHỜ DUYỆT</c:if> 
            </h2>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder" />
            <c:url value="Admin/${nameBill}.htm" var="pagedLink">
                <c:param name="p" value="tuong" />
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}" />
            </div>
            <table>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Mã khách hàng</th>
                    <th>Ngày tạo</th>
                    <th>Ngày nhận</th>
                    <th>Tổng tiền</th>
                    <th>Mã NV giao</th>
                    <th>Mã NV duyệt</th>
                    <th>Xem chi tiết</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maDH}</td>
                        <td>${s.khachHang.userId}</td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayTao}" /></td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayNhan}" /></td>
                        <td><fmt:formatNumber type = "currency" value = "${s.tongTien}" /></td>
                        <td>${s.nhanVienG.userId}</td>
                        <td>${s.nhanVienD.userId}</td>
                        <td><a href="Admin/billDetail/${s.maDH}.htm">Xem chi tiết</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>