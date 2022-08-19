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
        <form action="${pageContext.request.contextPath}/Admin/promotion.htm"
              method="post" class="form-horizontal">
            <label>Ngay bat dau</label>
            <input type="date" name="ngayBD"/>
            <label>Ngay ket thuc</label>
            <input type="date" name="ngayKT"/>
            <label>Mo ta</label>
            <textarea cols="20" rows="10" name="moTa"></textarea>
            
            <div id="template">
                <select name="maSP">
                    <c:forEach var = "i" items="${dsSanPham}">
                        <option value="${i.maSP}">${i.tenSP}</option>
                    </c:forEach>
                </select>
                <label>Giam gia</label>
                <input type="number" min="0" max="99" name="giamGia" />
            </div>

            <div id="container">
            </div>
            <button name="btnMoreProduct" onclick="moreProduct(event)">Thêm s?n ph?m</button>
            <button name="${btnStatus}">Lưu</button>
        </form>
        <script>
            var templete = document.getElementById("template");
            function moreProduct(event) {
                event.preventDefault();
                let container = document.getElementById("container");
                let select = templete.cloneNode(true);
                console.log(select);
                container.appendChild(select);
            }

        </script>
    </body>
</html>
