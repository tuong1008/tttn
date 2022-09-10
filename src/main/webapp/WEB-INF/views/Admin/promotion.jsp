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
        <title>Insert title here</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <%@include file="/WEB-INF/views/Admin/menu.jsp" %>
        <div class="container">
            <h2>THÊM KHUYẾN MÃI</h2>
            <form action="${pageContext.request.contextPath}/Admin/promotion.htm"
                  method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="form-element">
                        <label>Ngày bắt đầu</label>
                        <input id="from" type="date" name="ngayBD" required="required"/>    
                    </div>
                    <div class="form-element">
                        <label>Ngày kết thúc</label>
                        <input id="to" type="date" name="ngayKT" required="required"/>    
                    </div>
                    <div>
                        <label>Mô tả</label>
                        <textarea style="width: 100%" cols="20" rows="10" name="moTa"></textarea>    
                    </div>

                    <div id="template">
                        <select name="maSP">
                            <c:forEach var = "i" items="${dsSanPham}">
                                <option value="${i.maSP}">${i.tenSP}</option>
                            </c:forEach>
                        </select>
                        <label>Giảm giá(%)</label>
                        <input type="number" min="0" max="99" name="giamGia" />
                    </div>

                    <div id="container">
                    </div>
                    <div>
                        <button style="margin: 5px auto" name="btnMoreProduct" onclick="moreProduct(event)">Thêm sản phẩm</button>
                        <button style="margin: 5px auto" name="${btnStatus}" onclick="save(event)">Lưu</button>
                    </div>
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
                function save(event) {
			let from = new Date(document.getElementById("from").value);
			let to = new Date(document.getElementById("to").value);
			if(from > to){
				alert("Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc");
				event.preventDefault();
			}
		}

            </script>
    </body>
</html>
