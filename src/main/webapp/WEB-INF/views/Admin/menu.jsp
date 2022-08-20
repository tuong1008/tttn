<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<header>
    <div class="header__logo">
        <img style="max-width: 50px" src="resource/img/nike_logo.png" alt="">
    </div>
    <div class="header__right">
        <a class="header__logout" href="Admin/login.htm"><i class="fas fa-sign-out-alt"></i></a>
    </div>
</header>
<nav
        style="background-color: grey;
            max-height: 2000px;
            height: 100%;
            margin: 0px auto;
            overflow: scroll;">
    <ul class="list__task">
        <li class="task__item">
            <a href="Admin/info.htm" style="margin: auto;
               font-weight: bold;
               text-align: center;
               ">Thông tin cá nhân</a>
        </li>
        <c:if test="${sessionScope.staff.taiKhoan.quyen.maQuyen==4}">
            <li class="task__item">
                <a href="Admin/bill.htm"> <i class="fas fa-user-clock"></i> Quản lí đơn hàng </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.staff.taiKhoan.quyen.maQuyen==1}">
            <li class="task__item">
                <a href="Admin/staff.htm"> <i class="fas fa-user-clock"></i> Quản lí nhân viên </a>
            </li>
            <li class="task__item">
                <a href="Admin/customer.htm"> <i class="fas fa-user-clock"></i> Quản lí khách hàng </a>
            </li>
        </c:if>
        <c:if test="${sessionScope.staff.taiKhoan.quyen.maQuyen!=4}">
            <li class="task__item">
                <a href="Admin/product-type.htm"> <i class="fab fa-product-hunt"></i> Quản lí loại sản phẩm</a>
            </li>
            <li class="task__item">
                <a href="Admin/product.htm"> <i class="fab fa-product-hunt"></i> Quản lí sản phẩm </a>
            </li>
            <li class="task__item">
                <a href="Admin/supplier.htm"> <i class="fab fa-product-hunt"></i> Quản lí nhà cung cấp </a>
            </li>
            <li class="task__item">
                <a> <i class="fas fa-file-invoice-dollar"></i> Quản lí hóa đơn</a>
                <ul class="list__sub-task">
                    <li class="sub-task__item">
                        <a href="Admin/billUnConfirm.htm">Đơn hàng chờ xử lý</a>
                    </li>
                    <li class="sub-task__item">
                        <a href="Admin/billConfirm.htm">Đơn hàng đã hoàn thành</a>
                    </li>
                </ul>
            </li>
            <li class="task__item">
                <a> <i class="fas fa-file-invoice-dollar"></i> Quản lí đặt hàng</a>
                <ul class="list__sub-task">
                    <li class="sub-task__item">
                        <a href="Admin/orderMng.htm">Danh sách phiếu đặt</a>
                    </li>
                    <li class="sub-task__item">
                        <a href="Admin/order.htm">Phiếu đặt</a>
                    </li>
                </ul>
            </li>
        </c:if>

        <c:if test="${sessionScope.staff.taiKhoan.quyen.maQuyen!=4 && sessionScope.staff.taiKhoan.quyen.maQuyen!=3}">
            <li class="task__item">
                <a href="Admin/promotion.htm"> <i class="fas fa-file-invoice-dollar"></i> Quản lí khuyến mãi</a>
            </li>
            <li class="task__item">
                <a href="Admin/statistic.htm"> <i class="fas fa-chart-area"></i> Thống kê </a>
                <ul class="list__sub-task">
                    <li class="sub-task__item">
                        <a href="Admin/statistic.htm">Doanh thu</a>
                    </li>
                    <li class="sub-task__item">
                        <a href="Admin/statisticByBrand.htm">Doanh thu theo nhãn hiệu</a>
                    </li>
                    <li class="sub-task__item">
                        <a href="Admin/statisticTopProducts.htm">Top doanh thu theo sản phẩm</a>
                    </li>
                </ul>
            </li>
        </c:if>
    </ul>
</nav>