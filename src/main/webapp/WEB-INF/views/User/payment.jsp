<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link href="<c:url value='/resource/css/User/menuStyle.css' />"
              rel="stylesheet">
        <link href="<c:url value='/resource/css/Style.css' />" rel="stylesheet">
        <link
            href="<c:url value='/resource/fontawesome-free-5.15.4-web/css/all.css' />"
            rel="stylesheet">
        <base href="${pageContext.servletContext.contextPath}/">
        <style type="text/css">
            .info-app {
                padding: 24px;
                display: flex;
                align-items: center;
                margin: 200px 100px;
                margin-bottom: 100px;
                padding-top: 12px;
                position: relative;
            }

            .info-login {
                text-align: center;
            }

            .info-login img {
                width: 50%;
                display: block;
                margin-bottom: 12px;
            }

            .info-login p {
                color: #007bff;
                font-size: 20px;
                cursor: pointer;
            }

            .info-user {
                transform: translate(-30%, 0);
                /* border: solid 1px; */
                padding: 24px;
                border-radius: 5px;
                box-shadow: 0 0 4px 4px rgb(134, 131, 131);
            }

            .info-form {
                margin: 24px 0;
            }

            label {
                display: inline-block;
                width: 120px;
            }

            input, select {
                font-size: 20px;
                width: 300px;
                padding: 4px;
                border: solid 1px;
            }

            h1 {
                text-align: center;
            }

            .form__item {
                margin-bottom: 12px;
                font-size: 20px;
            }

            button, .a-btn {
                padding: 8px 24px;
                background-color: #007bff;
                color: white;
                cursor: pointer;
                border: none;
                font-size: 20px;
                text-decoration: none;
            }

            .info__btn, .pass__btn {
                display: flex;
                margin-top: 24px;
            }

            .a-btn:hover, button:hover {
                background-color: blue;
            }

            #overlay {
                position: fixed;
                background-color: #18171790;
                left: 0;
                right: 0;
                top: 0;
                bottom: 0;
                opacity: 1;
                display: none;
            }

            .pass-form {
                padding: 24px;
                border-radius: 5px;
                box-shadow: 0 0 4px 4px rgb(134, 131, 131);
                display: inline-block;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: white;
            }

            .btn__item {
                display: inline-block;
            }
        </style>
        <title>Insert title here</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <header class="header-app">
            <%@include file="/WEB-INF/views/User/Menu/header.jsp" %>
        </header>

        <div class="info-app">
            <div class="info-user">
                <h1>THÔNG TIN THANH TOÁN, ĐẶT HÀNG</h1>
                <form:form id="myForm" class="info-form" action="User/payment.htm" modelAttribute="donHang" onsubmit="beforeSubmitOrder()">
                    <div class="form__item">
                        <label for="">Họ và tên</label>
                        <form:input type="text" path="hoTenNN" placeholder="Họ và tên"/>
                        <form:errors class="error" path="hoTenNN"/>
                    </div>
                    <div class="form__item">
                        <label for="">Số điện thoại</label>
                        <form:input type="number" path="sdtNN" placeholder="Số điện thoại"/>
                        <form:errors class="error" path="sdtNN"/>
                    </div>
                    <div class="form__item">
                        <label for="">Email</label>
                        <form:input type="email" path="emailNN" placeholder="Email"/>
                        <form:errors class="error" path="emailNN"/>
                    </div>
                    <div class="form__item">
                        <form:input type="hidden" path="diaChiNN" placeholder="Địa chỉ"/>
                        <form:errors class="error" path="diaChiNN"/>
                    </div>
                    <div class="form__item">
                        <label for="">Thành phố</label>
                        <select name="adminDivision1" id="adminDivision1" onchange="adminDivision1Event(event)">
                            <option value="Thành phố Thủ Đức" selected="selected">Thành phố Thủ Đức</option>
                            <option value="Quận Bình Thạnh">Quận Bình Thạnh</option>
                            <option value="Quận Gò Vấp">Quận Gò Vấp</option>
                        </select>
                    </div>
                    <div class="form__item">
                        <label for="">Phường</label>
                        <select name="adminDivision2" id="adminDivision2">
                            <option value="Phường An Khánh" selected="selected">Phường An Khánh</option>
                            <option value="Phường An Lợi Đông">Phường An Lợi Đông</option>
                            <option value="Phường An Phú">Phường An Phú</option>
                            <option value="Phường Bình Chiểu">Phường Bình Chiểu</option>
                            <option value="Phường Bình Thọ">Phường Bình Thọ</option>
                            <option value="Phường Bình Trưng Đông">Phường Bình Trưng Đông</option>
                            <option value="Phường Bình Trưng Tây">Phường Bình Trưng Tây</option>
                            <option value="Phường Cát Lái">Phường Cát Lái</option>
                            <option value="Phường Hiệp Bình Chánh">Phường Hiệp Bình Chánh</option>
                            <option value="Phường Hiệp Bình Phước">Phường Hiệp Bình Phước</option>
                            <option value="Phường Hiệp Phú">Phường Hiệp Phú</option>
                            <option value="Phường Linh Chiểu">Phường Linh Chiểu</option>
                            <option value="Phường Linh Đông">Phường Linh Đông</option>
                            <option value="Phường Linh Tây">Phường Linh Tây</option>
                            <option value="Phường Linh Trung">Phường Linh Trung</option>
                            <option value="Phường Linh Xuân">Phường Linh Xuân</option>
                            <option value="Phường Long Bình">Phường Long Bình</option>
                            <option value="Phường Long Phước">Phường Long Phước</option>
                            <option value="Phường Long Thạnh Mỹ">Phường Long Thạnh Mỹ</option>
                            <option value="Phường Long Trường">Phường Long Trường</option>
                            <option value="Phường Phú Hữu">Phường Phú Hữu</option>
                            <option value="Phường Phước Bình">Phường Phước Bình</option>
                            <option value="Phường Phước Long A">Phường Phước Long A</option>
                            <option value="Phường Phước Long B">Phường Phước Long B</option>
                            <option value="Phường Tam Bình">Phường Tam Bình</option>
                            <option value="Phường Tam Phú">Phường Tam Phú</option>
                            <option value="Phường Tăng Nhơn Phú A">Phường Tăng Nhơn Phú A</option>
                            <option value="Phường Tăng Nhơn Phú B">Phường Tăng Nhơn Phú B</option>
                            <option value="Phường Tân Phú">Phường Tân Phú</option>
                            <option value="Phường Thảo Điền">Phường Thảo Điền</option>
                            <option value="Phường Thạnh Mỹ Lợi">Phường Thạnh Mỹ Lợi</option>
                            <option value="Phường Thủ Thiêm">Phường Thủ Thiêm</option>
                            <option value="Phường Trường Thạnh">Phường Trường Thạnh</option>
                            <option value="Phường Trường Thọ">Phường Trường Thọ</option>
                        </select>
                    </div>
                    <div class="form__item">
                        <label for="">Số nhà, Đường</label>
                        <input id="adminDivision3" type="text" required="required"/>
                    </div>
                    <div class="form__item">
                        <label for="">Tổng tiền</label>
                        <form:input type="text" path="tongTien" readonly="true"
                                    placeholder="Tổng tiền"/>
                        <form:errors class="error" path="tongTien"/>
                    </div>
                    <form:input type="hidden" path="khachHang.userId"/>
                    <form:input type="hidden" path="ngayTao"/>
                    <form:input type="hidden" path="maDH"/>
                    <form:input type="hidden" path="trangThai"/>
                    <button class="info__btn" name="btnBook">Đặt hàng</button>
                    ${message}
                </form:form>
            </div>
        </div>

        <header class="footer-app">
            <%@include file="/WEB-INF/views/User/Menu/footer.jsp" %>
        </header>

        <script>
            let thanhPhoThuDuc = `
        <option value="Phường An Khánh" selected="selected">Phường An Khánh</option>
        <option value="Phường An Lợi Đông">Phường An Lợi Đông</option>
        <option value="Phường An Phú">Phường An Phú</option>
        <option value="Phường Bình Chiểu">Phường Bình Chiểu</option>
        <option value="Phường Bình Thọ">Phường Bình Thọ</option>
        <option value="Phường Bình Trưng Đông">Phường Bình Trưng Đông</option>
        <option value="Phường Bình Trưng Tây">Phường Bình Trưng Tây</option>
        <option value="Phường Cát Lái">Phường Cát Lái</option>
        <option value="Phường Hiệp Bình Chánh">Phường Hiệp Bình Chánh</option>
        <option value="Phường Hiệp Bình Phước">Phường Hiệp Bình Phước</option>
        <option value="Phường Hiệp Phú">Phường Hiệp Phú</option>
        <option value="Phường Linh Chiểu">Phường Linh Chiểu</option>
        <option value="Phường Linh Đông">Phường Linh Đông</option>
        <option value="Phường Linh Tây">Phường Linh Tây</option>
        <option value="Phường Linh Trung">Phường Linh Trung</option>
        <option value="Phường Linh Xuân">Phường Linh Xuân</option>
        <option value="Phường Long Bình">Phường Long Bình</option>
        <option value="Phường Long Phước">Phường Long Phước</option>
        <option value="Phường Long Thạnh Mỹ">Phường Long Thạnh Mỹ</option>
        <option value="Phường Long Trường">Phường Long Trường</option>
        <option value="Phường Phú Hữu">Phường Phú Hữu</option>
        <option value="Phường Phước Bình">Phường Phước Bình</option>
        <option value="Phường Phước Long A">Phường Phước Long A</option>
        <option value="Phường Phước Long B">Phường Phước Long B</option>
        <option value="Phường Tam Bình">Phường Tam Bình</option>
        <option value="Phường Tam Phú">Phường Tam Phú</option>
        <option value="Phường Tăng Nhơn Phú A">Phường Tăng Nhơn Phú A</option>
        <option value="Phường Tăng Nhơn Phú B">Phường Tăng Nhơn Phú B</option>
        <option value="Phường Tân Phú">Phường Tân Phú</option>
        <option value="Phường Thảo Điền">Phường Thảo Điền</option>
        <option value="Phường Thạnh Mỹ Lợi">Phường Thạnh Mỹ Lợi</option>
        <option value="Phường Thủ Thiêm">Phường Thủ Thiêm</option>
        <option value="Phường Trường Thạnh">Phường Trường Thạnh</option>
        <option value="Phường Trường Thọ">Phường Trường Thọ</option>    
    `;
            let quanBinhThanh = `
        <option value="Phường 01" selected="selected">Phường 01</option>
        <option value="Phường 02">Phường 02</option>
        <option value="Phường 03">Phường 03</option>
        <option value="Phường 05">Phường 05</option>
        <option value="Phường 06">Phường 06</option>
        <option value="Phường 07">Phường 07</option>
        <option value="Phường 11">Phường 11</option>
        <option value="Phường 12">Phường 12</option>
        <option value="Phường 13">Phường 13</option>
        <option value="Phường 14">Phường 14</option>
        <option value="Phường 15">Phường 15</option>
        <option value="Phường 17">Phường 17</option>
        <option value="Phường 19">Phường 19</option>
        <option value="Phường 21">Phường 21</option>
        <option value="Phường 22">Phường 22</option>
        <option value="Phường 24">Phường 24</option>
        <option value="Phường 25">Phường 25</option>
        <option value="Phường 26">Phường 26</option>
        <option value="Phường 27">Phường 27</option>
        <option value="Phường 28">Phường 28</option>
    `;
            let quanGoVap = `
        <option value="Phường 01" selected="selected">Phường 01</option>
        <option value="Phường 03">Phường 03</option>
        <option value="Phường 04">Phường 04</option>
        <option value="Phường 05">Phường 05</option>
        <option value="Phường 06">Phường 06</option>
        <option value="Phường 07">Phường 07</option>
        <option value="Phường 08">Phường 08</option>
        <option value="Phường 09">Phường 09</option>
        <option value="Phường 10">Phường 10</option>
        <option value="Phường 11">Phường 11</option>
        <option value="Phường 12">Phường 12</option>
        <option value="Phường 13">Phường 13</option>
        <option value="Phường 14">Phường 14</option>
        <option value="Phường 15">Phường 15</option>
        <option value="Phường 16">Phường 16</option>
        <option value="Phường 17">Phường 17</option>
    `;
            function adminDivision1Event(event){
                let adminDivision1 = event.target.value;
                let adminDivision2Select = document.getElementById('adminDivision2');
                if (adminDivision1 == 'Thành phố Thủ Đức'){
                    adminDivision2Select.innerHTML = thanhPhoThuDuc;
                } else if (adminDivision1 == 'Quận Bình Thạnh'){
                    adminDivision2Select.innerHTML = quanBinhThanh;
                } else {
                    adminDivision2Select.innerHTML = quanGoVap;
                }
            }
            function beforeSubmitOrder(){
                let adminDivision1 = document.getElementById('adminDivision1').value;
                let adminDivision2 = document.getElementById('adminDivision2').value;
                let adminDivision3 = document.getElementById('adminDivision3').value;
                
                let diaChiNNInput = document.getElementById('diaChiNN');
                diaChiNNInput.value = adminDivision3+', '+adminDivision2+', '+adminDivision1;
            }
        </script>
    </body>
</html>