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
        <form action="${pageContext.request.contextPath}/Admin/import/${maPD}.htm"
              method="post" class="form-horizontal">
            <c:forEach var = "i" items="${ctPhieuDat}">
                <div>
                    <label>${i.pk.sanPham.tenSP}</label>
                    <input name="maSP" value="${i.pk.sanPham.maSP}" type="hidden"/>
                    <input name="gia" value="${i.gia}" readonly="true"/>
                    <input name="sl" value="${i.sl}" min="0" max="${i.sl}" type="number"/>
                </div>
            </c:forEach>
            <button name="${btnStatus}">Lưu</button>
        </form>

    </body>
</html>
