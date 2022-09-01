<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
            <h2>DANH SÁCH KHUYẾN MÃI</h2>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="Admin/promotionList.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>

            </div>
            <table>
                <tr>
                    <th>Mã khuyến mãi</th>
                    <th>Ngày bắt đầu</th>
                    <th>Ngày kết thúc</th>
                    <th>Mô tả</th>
                    <th>Mã nhân viên</th>
                    <th>Xem</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maKM}</td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayBD}" /></td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayKT}" /></td>
                        <td>${s.moTa}</td>
                        <td>${s.nhanVien.maNV}</td>
                        <td><a href="Admin/promotionDetail/${s.maKM}.htm">Chi tiết</a></td>
                        <td><a href="Admin/promotionEdit/${s.maKM}.htm?linkEdit">Sửa</a></td>
                        <td><a href="Admin/promotionList/${s.maKM}.htm?linkDelete">Xóa</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
