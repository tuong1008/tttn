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
            <h2>SỬA KHUYẾN MÃI</h2>
            <form action="${pageContext.request.contextPath}/Admin/promotion/${maKM}.htm"
                  method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="form-element">
                        <label>Ngày bắt đầu</label>
                        <input type="date" name="ngayBD" value="${ngayBD}"/>    
                    </div>
                    <div class="form-element">
                        <label>Ngày kết thúc</label>
                        <input type="date" name="ngayKT" value="${ngayKT}"/>    
                    </div>
                    <div>
                        <label>Mô tả</label>
                        <textarea style="width: 100%" cols="20" rows="10" name="moTa">${moTa}</textarea>    
                    </div>

                    <div id="template" style="display: none">
                        <select name="maSP">
                            <c:forEach var = "i" items="${dsSanPham}">
                                <option value="${i.maSP}">${i.tenSP}</option>
                            </c:forEach>
                        </select>
                        <label>Giảm giá(%)</label>
                        <input type="number" min="0" max="99" name="giamGia" value="${ct.giamGia}"/>
                    </div>

                    <div id="container">
                        <c:forEach var="ct" items="${ctKM}">
                            <div>
                                <select name="maSP">
                                    <c:forEach var = "i" items="${dsSanPham}">
                                        <option value="${i.maSP}" ${ct.pk.sanPham.maSP == i.maSP ? "selected":""}>${i.tenSP}</option>
                                    </c:forEach>
                                </select>
                                <label>Giảm giá(%)</label>
                                <input type="number" min="0" max="99" name="giamGia" value="${ct.giamGia}"/>
                            </div>
                        </c:forEach>
                    </div>
                    <div>
                        <button style="margin: 5px auto" name="btnMoreProduct" onclick="moreProduct(event)">Thêm sản phẩm</button>
                        <button style="margin: 5px auto" name="${btnStatus}">Lưu</button>
                    </div>
            </form>
            <script>
                var templete = document.getElementById("template");
                function moreProduct(event) {
                    event.preventDefault();
                    let container = document.getElementById("container");
                    let select = templete.cloneNode(true);
                    select.style="";
                    console.log(select);
                    container.appendChild(select);
                }

            </script>
    </body>
</html>
