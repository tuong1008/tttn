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
        <form:form action="${pageContext.request.contextPath}/Admin/order.htm"
               enctype="multipart/form-data" method="post" modelAttribute="order" class="form-horizontal">
            <form:select onchange="loadProduct()" id="nhaCungCap" path="nhaCungCap">
            <form:options items="${suppliers}"/>
        </form:select>
        <form:errors path="nhaCungCap" cssClass="text-danger"/>
        
        <div id="container">
        </div>
        <button name="${btnStatus}">Lưu</button>
    </form:form>
        <script>
            function loadProduct(){
                let e = document.getElementById("nhaCungCap");
                let value = e.value;
                let url = "http://localhost:8080/tttn/Admin/product-supplier.htm?maNCC="+value;
            fetch(url, {
                method: "GET",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(object)
            })
                .then(function (response) {
                    return response.json();
                })
                .then(result => {
                    console.log(result);
                    let container = document.getElementById("container");
                    let select = document.createElement('select');
                    for (let i = 0 ; i<result.length ; i++){
                        select.name = "SanPham";
                        let option = document.createElement('option');
                        select.appendChild(option);
                    }
                    container.appendChild(select)
                })
                .catch(err => {
                    console.log(err);
                });
            }
            
        </script>
    </body>
</html>
