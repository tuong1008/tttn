<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>
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
            .cart-app {
                margin: 200px 100px;
                font-size: 20px;
                margin-bottom: 300px;
            }

            .cart-app__title {
                display: flex;
                margin-bottom: 24px;
                border-radius: 5px;
                box-shadow: 0 0 2px 2px rgb(202, 195, 195);
                text-align: center;
                padding: 16px 0;
            }


            .title__item, .cart-detail__item {
                flex: 1;
                /* border: solid 1px; */
            }

            input {
                width: 40px;
                display: inline-block;
                font-size: 20px;
                border: none;
            }

            .cart__delete {
                display: block;
                padding: 4px;
                font-size: 20px;
                border: none;
                cursor: pointer;
                color: white;
                background-color: #007bff;
                border-radius: 4px;
                transition: all 0.5s;
            }

            .cart__delete:hover {
                background-color: #095bb3;
            }

            .cart__item {
                display: flex;
                align-items: center;
                text-align: center;
                border-radius: 5px;
                box-shadow: 0 0 2px 2px rgb(202, 195, 195);
                margin-bottom: 12px;
                padding: 12px 0;
            }

        </style>
        <title>Insert title here</title>
        <base href="${pageContext.servletContext.contextPath}/">
    </head>
    <body>
        <header class="header-app">
            <%@include file="/WEB-INF/views/User/Menu/header.jsp" %>
        </header>


        <div class="cart-app">

            <jsp:useBean id="pagedListHolder" scope="request"
                         type="org.springframework.beans.support.PagedListHolder"/>
            <c:url value="User/history.htm" var="pagedLink">
                <c:param name="p" value="tuong"/>
            </c:url>
            <div>
                <tg:paging pagedListHolder="${pagedListHolder}"
                           pagedLink="${pagedLink}"/>
                <style type="text/css">
                    .fs-search {
                        display: none;
                    }
                </style>
            </div>

            <div class="cart-app__title">
                <div class="title__item">H??a ????n</div>
                <div class="title__item">Tr???ng th??i</div>
                <div class="title__item">H??? t??n nh???n</div>
                <div class="title__item">SDT nh???n</div>
                <div class="title__item">Ng??y t???o</div>
                <div class="title__item">Ng??y nh???n</div>
                <div class="title__item">S??? ti???n</div>
                <div class="title__item">H??? t??n nh??n vi??n v???n chuy???n</div>
                <div class="title__item">Thao t??c</div>
                <div class="title__item"></div>
            </div>
            <div class="cart__list">

                <c:forEach var="b" items="${pagedListHolder.pageList}">
                    <div class="cart__item">
                        <div class="cart-detail__item"><label>${b.maDH}</label></div>
                        <div class="cart-detail__item"><label>${b.trangThai}</label></div>
                        <div class="cart-detail__item"><label>${b.hoTenNN}</label></div>
                        <div class="cart-detail__item"><label>${b.sdtNN}</label></div>
                        <div class="cart-detail__item"><label>${b.ngayTao}</label></div>
                        <div class="cart-detail__item"><label>${b.ngayNhan}</label></div>

                        <div class="cart-detail__item"><fmt:formatNumber type="currency" value="${b.tongTien}"/></div>
                        <div class="cart-detail__item"><label>${b.nhanVienG}</label></div>

                        <div class="cart-detail__item">
                            <a class="cart__delete" href="User/history/idBill=${b.maDH}.htm?linkDetail">Xem chi ti???t</a>
                        </div>

                        <div class="cart-detail__item">
                            <c:if test="${b.trangThai == 'Ch??? x??c nh???n'}">
                                <a class="cart__delete" href="User/history/idBill=${b.maDH}.htm?linkDelete">H???y</a>
                            </c:if>
                        </div>

                    </div>
                </c:forEach>

            </div>
        </div>

        <footer class="footer-app">
            <%@include file="/WEB-INF/views/User/Menu/footer.jsp" %>
        </footer>
    </body>
</html>