<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
        <h2>DANH SÁCH PHI?U Ð?T</h2>
        <jsp:useBean id="pagedListHolder" scope="request"
                     type="org.springframework.beans.support.PagedListHolder"/>
    <c:url value="Admin/orderMng.htm" var="pagedLink">
        <c:param name="p" value="~"/>
    </c:url>
    <div>
        <tg:paging pagedListHolder="${pagedListHolder}"
                   pagedLink="${pagedLink}"/>

    </div>
    <table>
        <tr>
            <th>Mã phieu dat</th>
            <th>Ngay tao</th>
            <th>Ma nha cung cap</th>
            <th>Ma nhan vien</th>
            <th>Action</th>
        </tr>
        <c:forEach var="s" items="${pagedListHolder.pageList}">
            <tr>
            <td>${s.maPD}</td>
            <td><fmt:formatDate pattern="dd-MM-yyyy" value="${s.ngayTao}" /></td>
                <td>${s.nhaCungCap.maNCC}</td>
                <td>${s.nhanVien.maNV}</td>
                <td><a href="order/${s.maPD}.htm?linkEdit">Sửa</a></td>
                <td>
                    <a href="order/${s.maPD}.htm?linkDelete">Xóa</a>
                </td>
                <td>
                    <a href="import/${s.maPD}.htm">Tao Phieu Nhap</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
