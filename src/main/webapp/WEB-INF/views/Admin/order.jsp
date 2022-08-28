<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
            <h2>THÊM PHIẾU ĐẶT</h2>
            <form action="${pageContext.request.contextPath}/Admin/order.htm"
                  method="post" class="form-horizontal">
                <label class="label-title" for="">Nhà cung cấp : </label>
                <select onchange="loadProduct()" name="nhaCungCap" id="nhaCungCap">
                    <c:forEach var = "i" items="${suppliers}">
                        <option value="${i.maNCC}">${i.tenNCC}</option>
                    </c:forEach>
                </select>

                <div class="form-group" id="container">
                </div>
                <button style="margin: 5px auto" name="btnMoreProduct" onclick="moreProduct(event)">Thêm sản phẩm</button>
                <button style="margin: 5px auto" name="${btnStatus}">Lưu</button>
            </form>
        </div>
        <script>
            var select;
            loadProduct();
            function loadProduct() {
                document.getElementById("container").innerHTML = "";
                select = document.createElement('select');
                select.style = "width: 200px;margin: 10px 20px 10px 5px;"
                let e = document.getElementById("nhaCungCap");
                let value = e.value;
                let url = "http://localhost:8080/tttn/Admin/product-supplier.htm?maNCC=" + value;
                fetch(url, {
                    method: "GET",
                    headers: {'Content-Type': 'application/json'}
                })
                        .then(function (response) {
                            return response.json();
                        })
                        .then(result => {
                            console.log(result);
                            let container = document.getElementById("container");
                            for (let i = 0; i < result.length; i++) {
                                select.name = "sanPham";
                                let option = document.createElement('option');
                                option.value = result[i].maSP;
                                option.innerHTML = result[i].tenSP;
                                select.appendChild(option);
                            }
                            let div = document.createElement("div");
                            div.class = "form-element";
                            let labelTenSP = document.createElement("label");
                            labelTenSP.innerHTML = "Tên sản phẩm";
                            let labelGia = document.createElement("label");
                            labelGia.innerHTML = "Giá";
                            let labelSL = document.createElement("label");
                            labelSL.innerHTML = "Số lượng";
                            let input = document.createElement("input");
                            let inputGia = document.createElement("input");
                            input.type = "number";
                            input.name = "soLuong";
                            input.min = 1;
                            input.style = "width: 50px;margin: 10px 20px 10px 5px;";
                            inputGia.type = "number";
                            inputGia.name = "gia";
                            inputGia.style = "width: 80px;margin: 10px 20px 10px 5px;";
                            div.appendChild(labelTenSP)
                            div.appendChild(select);
                            div.appendChild(labelGia);
                            div.appendChild(inputGia);
                            div.appendChild(labelSL);
                            div.appendChild(input);
                            container.appendChild(div);
                        })
                        .catch(err => {
                            console.log(err);
                        });
            }
            function moreProduct(event) {
                event.preventDefault();
                let div = document.createElement("div");
                div.class = "form-element";
                let labelTenSP = document.createElement("label");
                labelTenSP.innerHTML = "Tên sản phẩm";
                let labelGia = document.createElement("label");
                labelGia.innerHTML = "Giá";
                let labelSL = document.createElement("label");
                labelSL.innerHTML = "Số lượng";
                let input = document.createElement("input");
                input.type = "number";
                input.name = "soLuong";
                input.min = 1;
                input.style = "width: 50px;margin: 10px 20px 10px 5px;";
                let inputGia = document.createElement("input");
                inputGia.type = "number";
                inputGia.name = "gia";
                inputGia.style = "width: 80px;margin: 10px 20px 10px 5px;";
                let newSelect = select.cloneNode(true);
                div.appendChild(labelTenSP)
                div.appendChild(newSelect);
                div.appendChild(labelGia);
                div.appendChild(inputGia);
                div.appendChild(labelSL);
                div.appendChild(input);
                let container = document.getElementById("container");
                container.appendChild(div);
            }

        </script>
    </body>
</html>
