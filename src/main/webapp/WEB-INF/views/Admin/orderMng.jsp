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
            <h2>DANH SÁCH PHIẾU ĐẶT</h2>
            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="Admin/orderMng.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>

            </div>
            <table>
                <tr>
                    <th>Mã phiếu đặt</th>
                    <th>Ngày tạo</th>
                    <th>Mã nhà cung cấp</th>
                    <th>Mã nhân viên</th>
                    <th>Sửa</th>
                    <th>Xóa</th>
                    <th>Tạo phiếu nhập</th>
                </tr>
                <c:forEach var="s" items="${pagedListHolder.pageList}">
                    <tr>
                        <td>${s.maPD}</td>
                        <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayTao}" /></td>
                        <td>${s.nhaCungCap.maNCC}</td>
                        <td>${s.nhanVien.maNV}</td>
                        <td><a href="Admin/order/${s.maPD}.htm?linkEdit">Sửa</a></td>
                        <td>
                            <a href="Admin/order/${s.maPD}.htm?linkDelete">Xóa</a>
                        </td>
                        <td>
                            <c:if test="${empty s.phieuNhap}">
                                <a href="Admin/import/${s.maPD}.htm">Tạo phiếu nhập</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </body>
</html>
