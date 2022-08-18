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
        <form action="${pageContext.request.contextPath}/Admin/order.htm"
                   method="post" class="form-horizontal">
            <select onchange="loadProduct()" name="nhaCungCap" id="nhaCungCap">
                <c:forEach var = "i" items="${suppliers}">
                    <option value="${i.maNCC}">${i.tenNCC}</option>
                </c:forEach>
            </select>
            

            <div id="container">
            </div>
            <button name="btnMoreProduct" onclick="moreProduct(event)">Thêm s?n ph?m</button>
            <button name="${btnStatus}">Lưu</button>
        </form>
        <script>
            var select;
            loadProduct();
            function loadProduct() {
                document.getElementById("container").innerHTML="";
                select = document.createElement('select');
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
                            let input = document.createElement("input");
                            input.type = "number";
                            input.name = "soLuong";
                            div.appendChild(select);
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
                let input = document.createElement("input");
                input.type = "number";
                input.name = "soLuong";
                let newSelect = select.cloneNode(true);
                div.appendChild(newSelect);
                div.appendChild(input);
                let container = document.getElementById("container");
                container.appendChild(div);
            }

        </script>
    </body>
</html>
