<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <form:form action="${pageContext.request.contextPath}/admin/addNhaCungCap.htm"
               method="post" modelAttribute="tenNCC" class="form-horizontal">
        <form:errors path="productName" cssClass="text-danger"/>
        <form:input path="tenNCC" class="form-control" placeholder="Enter tenNCC"
                    id="tenNCC"/>
        <form:errors path="sdt" cssClass="text-danger"/>
        <form:input path="sdt" class="form-control" placeholder="Enter sdt"
                    id="sdt"/>
        <form:errors path="diaChi" cssClass="text-danger"/>
        <form:input path="diaChi" class="form-control" placeholder="Enter diaChi"
                    id="diaChi"/>
        <form:errors path="email" cssClass="text-danger"/>
        <form:input path="email" class="form-control" placeholder="Enter email"
                    id="email"/>
        <form:errors path="email" cssClass="text-danger"/>
    </form:form>
</body>
</html>
