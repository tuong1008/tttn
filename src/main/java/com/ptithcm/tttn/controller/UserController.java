package com.ptithcm.tttn.controller;

import com.ptithcm.tttn.DAO.*;
import com.ptithcm.tttn.common.Utils;
import com.ptithcm.tttn.entity.*;
import java.io.IOException;
import java.util.Date;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Controller
@Transactional
@RequestMapping("User/")
public class UserController {

    @Autowired
    SanPhamDAO sanPhamDAOImpl;

    @Autowired
    NhaCungCapDAO nhaCungCapDAOImpl;

    @Autowired
    TaiKhoanDAO taiKhoanImpl;

    @Autowired
    KhachHangDAO khachHangDAOImpl;

    @Autowired
    CTDonHangDAO ctDonHangDAOImpl;

    @Autowired
    DonHangDAO donHangDAOImpl;

    @Autowired
    ChiTietKMDAO chiTietKMDAOImpl;

    @Autowired
    LoaiSPDAO loaiSPDAOImpl;

    @Autowired
    QuyenDAO quyenDAO;

    @RequestMapping("home")
    public String index(HttpServletRequest request, HttpSession session, ModelMap model) {
        session.setAttribute("brands", nhaCungCapDAOImpl.getSuppliers());
        session.setAttribute("categorys", loaiSPDAOImpl.getListCategory());

        showProducts(request, model, sanPhamDAOImpl.getListProductToSale());

        List<SanPham> newProducts = sanPhamDAOImpl.getListNewProduct();

        List<SanPham> hotProducts = sanPhamDAOImpl.getListHotProdduct(5);

        List<SanPham> hotSaleProducts = sanPhamDAOImpl.getListHotSaleProduct(10);

        session.setAttribute("newProducts", newProducts);
        session.setAttribute("hotProducts", hotProducts);
        session.setAttribute("hotSaleProducts", hotSaleProducts);

        return "User/home";
    }

    @RequestMapping(value = "home", params = "btnSearchByName", method = RequestMethod.POST)
    public String fillProduct(HttpServletRequest request, ModelMap model) {
        model.addAttribute("products",
                sanPhamDAOImpl.getListProductByNameForSale(request.getParameter("nameProduct")));
        model.addAttribute("searchAll", "1");
        return "User/homeFilterResult";
    }

    @RequestMapping(value = "home", params = "btnSearch", method = RequestMethod.POST)
    public String filterProduct(HttpServletRequest request, HttpSession session, ModelMap model) {
        String[] nameBrands = request.getParameterValues("nameBrand");
        String fromPrice = request.getParameter("fromPrice");
        String toPrice = request.getParameter("toPrice");
        String[] nameCategories = request.getParameterValues("nameCategory");

        List<SanPham> products = sanPhamDAOImpl.getCustomListProduct(nameBrands, fromPrice, toPrice, nameCategories);
        showProducts(request, model, products);
        return "User/homeFilterResult";
    }

    @RequestMapping("home/{nameBrand}")
    public String fillBrand(HttpServletRequest request, ModelMap model, @PathVariable("nameBrand") String nameBrand) {
        showProducts(request, model, sanPhamDAOImpl.getListProductByNameBrandForSale(nameBrand));
        model.addAttribute("brandsss", nameBrand);
        return "User/home";
    }

    public void showProducts(HttpServletRequest request, ModelMap model, List<SanPham> sanPhams) {
        PagedListHolder pagedListHolder = new PagedListHolder(sanPhams);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(9);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

    @RequestMapping("login")
    public String login() {
        return "User/login";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        session.removeAttribute("detailBills");
        return "redirect:/User/home.htm";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, ModelMap model, HttpSession session) {
        String idCustomer = request.getParameter("name");
        String passCustomer = request.getParameter("pass");
        if (taiKhoanImpl.getRole(passCustomer, idCustomer).equals("Customer")) {
            System.out.println(1);
            NguoiDung k = khachHangDAOImpl.getCustomer(idCustomer);
            if (k.getTrangThai() == 0) {
                System.out.println(2);
                model.addAttribute("message", "Tài khoản của bạn đã bị khóa, vui lòng liên hệ để được mở khóa!!");
            } else {
                System.out.println(3);
                session.setAttribute("customer", k);
                System.out.println(4);
                session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(
                        donHangDAOImpl.getBillUnBuy(k.getUserId()).getMaDH()));
                System.out.println(5);
                return "redirect:/User/home.htm";
            }
        } else {
            model.addAttribute("message", "Tên đăng nhập hoặc mật khẩu không đúng");
        }
        return "User/login";
    }

    @RequestMapping("register")
    public String register(ModelMap model) {
        model.addAttribute("customer", new NguoiDung());
        return "User/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String addCustomer(HttpServletRequest request, ModelMap model, @ModelAttribute("customer") NguoiDung customer,
            HttpSession session, BindingResult errors) {

        String username = request.getParameter("userName");
        String pass = request.getParameter("pass");
        model.addAttribute("userName", username);
        model.addAttribute("pass", pass);
        if (validateCustomer(customer, errors)) {
            if (taiKhoanImpl.getAccount(username) != null) {
                model.addAttribute("message", "Tài khoản đã tồn tại");
                return "User/register";
            }
            TaiKhoan taiKhoan = new TaiKhoan(username, pass, quyenDAO.getRole(5), null, null);
            customer.setUserId(khachHangDAOImpl.nextPK("NguoiDung", "KH", "userId"));
            String error = khachHangDAOImpl.insertCustomer(customer, taiKhoan);

            if (error.equals("")) {
                session.setAttribute("customer", customer);
                donHangDAOImpl.insert(customer);
                session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(donHangDAOImpl.getBillUnBuy(customer.getUserId()).getMaDH()));
                return "redirect:/User/home.htm";
            } else {
                model.addAttribute("message", error);
            }
        }
        return "User/register";
    }

    @RequestMapping("info")
    public String info(HttpSession session, ModelMap model) {
        model.addAttribute("customer", session.getAttribute("customer"));
        return "User/info";
    }

    @RequestMapping(value = "changePass")
    public String editCustomerPass() {
        return "User/changePass";
    }

    @RequestMapping(value = "changePass", params = "btnUpdatePass", method = RequestMethod.POST)
    public String editCustomerPass(HttpServletRequest request, ModelMap model, HttpSession session) {
        String newPass = request.getParameter("newPass");
        String oldPass = request.getParameter("oldPass");
        String newPassReset = request.getParameter("newPassReset");
        String idCustomer = ((NguoiDung) session.getAttribute("customer")).getTaiKhoan().getTenDN();
        Boolean flag = true;
        if (taiKhoanImpl.getRole(oldPass, idCustomer).equals("")) {
            model.addAttribute("oldPassEr", "Mật khẩu cũ không chính xác");
            flag = false;
        }
        if (newPass.equals("")) {
            model.addAttribute("newPassEr", "Vui lòng nhập mật khẩu mới");
            flag = false;
        }
        if (!newPass.equals(newPassReset)) {
            model.addAttribute("newPassResetEr", "Mật khẩu nhập lại không khớp");
            flag = false;
        }
        if (!flag) {
            model.addAttribute("oldPass", oldPass);
            model.addAttribute("newPass", newPass);
            model.addAttribute("newPassReset", newPassReset);
            return "User/changePass";
        }
        Integer temp = taiKhoanImpl.updatePass(newPassReset, idCustomer);
        if (temp == 0) {
            model.addAttribute("message", "Thay đổi mật khẩu thất bại");
        }
        model.addAttribute("customer", session.getAttribute("customer"));
        return "User/info";
    }

    @RequestMapping("history")
    public String history(HttpServletRequest request, ModelMap model, HttpSession session) {
        showBills(request, model, session);
        return "User/history";
    }

    public void showBills(HttpServletRequest request, ModelMap model, HttpSession session) {
        List<DonHang> list = donHangDAOImpl.getBills(
                ((NguoiDung) session.getAttribute("customer")).getUserId());
        PagedListHolder pagedListHolder = new PagedListHolder(list);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);

    }

    @RequestMapping(value = "history/idBill={idBill}.htm", params = "linkDetail")
    public String historyBill(HttpServletRequest request, ModelMap model, @PathVariable("idBill") String idBill) {
        System.out.println("xin chao");
        showDetailBills(request, model, idBill);
        model.addAttribute("idBill", idBill);
        return "User/historyDetail";
    }
    
    @RequestMapping(value = "history/idBill={idBill}.htm", params = "linkDelete")
    public String cancelBill(HttpServletRequest request, HttpSession session, ModelMap model, @PathVariable("idBill") String idBill) {
        DonHang d = donHangDAOImpl.getOne(DonHang.class, idBill);
        d.setTrangThai("Đã hủy");
        donHangDAOImpl.update(d);
        showBills(request, model, session);
        return "User/history";
    }

    public void showDetailBills(HttpServletRequest request, ModelMap model, String idBill) {
        List<CTDonHang> list = ctDonHangDAOImpl.getDetailBills(idBill);
        PagedListHolder pagedListHolder = new PagedListHolder(list);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);

    }

    @RequestMapping("cart")
    public String cart(HttpServletRequest request, ModelMap model, HttpSession session) {
        showDetailBills(request, model, session);
        return "User/cart";
    }

    @RequestMapping(value = "cart/idBill={idBill}+idProduct={idProduct}.htm", params = "linkDelete")
    public String deleteCart(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("idProduct") String idProduct, @PathVariable("idBill") String idBill) {
        ctDonHangDAOImpl.delete(ctDonHangDAOImpl.getDetailBill(idBill, idProduct));

        session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(
                donHangDAOImpl.getBillUnBuy(
                        ((NguoiDung) session.getAttribute("customer")).getUserId()).getMaDH()));
        return "redirect:/User/cart.htm";
    }

    public void showDetailBills(HttpServletRequest request, ModelMap model, HttpSession session) {
        List<CTDonHang> list = (List<CTDonHang>) session.getAttribute("detailBills");
        long sum = 0L;

        for (CTDonHang k : list) {
            k.setGia(k.getPk().getSanPham().getGia()
                    * (100 - chiTietKMDAOImpl.getDiscount(k.getPk().getSanPham().getMaSP())) / 100);
            sum = sum + k.getGia() * k.getSl();
        }
        PagedListHolder pagedListHolder = new PagedListHolder(list);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("sum", sum);
        model.addAttribute("pagedListHolder", pagedListHolder);

    }

    @RequestMapping("product/{id}")
    public String product(ModelMap model, @PathVariable("id") String id) {
        model.addAttribute("p", sanPhamDAOImpl.getProduct(id));
        model.addAttribute("discount", chiTietKMDAOImpl.getDiscount(id));
        model.addAttribute("detailBill", new CTDonHang());
        return "User/product";
    }

    @RequestMapping(value = "info", params = "btnUpdate", method = RequestMethod.POST)
    public String editCustomer(HttpServletRequest request, ModelMap model,
            @ModelAttribute("customer") NguoiDung customer, BindingResult errors, HttpSession session) {
        if (validateCustomer(customer, errors)) {
            String error = khachHangDAOImpl.updateCustomer(customer);

            if (error.equals("")) {
                model.addAttribute("message", "Sửa thành công");
                session.setAttribute("customer", khachHangDAOImpl.getCustomer(
                        ((NguoiDung) session.getAttribute("customer")).getUserId()));
            } else {
                model.addAttribute("message", error);
            }
        }
        return "User/info";
    }

    public Boolean validateCustomer(@ModelAttribute("customer") NguoiDung customer, BindingResult errors) {
        String checkname = "([\\p{L}\\s]+){1,50}";
        String checkphone = "0[0-9]{9}";
        String checkemail = "^[A-Za-z0-9+_.-]+@(.+)$";
        String checkaddress = "([\\p{L}\\s\\d\\,]+){1,100}";
        String checkid = "[A-Za-z0-9]{1,10}";
        String checkpass = "[A-Za-z0-9]{1,16}";

        if (!customer.getHoTen().trim().matches(checkname)) {
            errors.rejectValue("hoTen", "customer",
                    "Họ tên không được để trống, chứa ký tự đặc biệt hoặc quá 50 ký tự!");
        }
        if (!customer.getSdt().trim().matches(checkphone)) {
            errors.rejectValue("sdt", "customer", "Số điện thoại không đúng!");
        }
        if (!customer.getEmail().trim().matches(checkemail)) {
            errors.rejectValue("email", "customer", "email không đúng định dạng!");
        }
        if (!customer.getDiaChi().trim().matches(checkaddress)) {
            errors.rejectValue("address", "customer",
                    "Địa chỉ không được để trống, chứa ký tự đặc biệt hoặc quá 100 ký tự!");
        }
        return !errors.hasErrors();
    }

    @RequestMapping(value = "product/{id}", params = "linkAdd")
    public String addProduct(ModelMap model, @PathVariable("id") String id, HttpSession session) {
        if (session.getAttribute("customer") == null) {
            return "redirect:/User/login.htm";
        }
        DonHang b = donHangDAOImpl.getBillUnBuy(((NguoiDung) session.getAttribute("customer")).getUserId());
        CTDonHang db = ctDonHangDAOImpl.getDetailBill(b.getMaDH(), id);

        if (db == null) {
            CTDonHang detailBill = new CTDonHang();
            SanPham p = sanPhamDAOImpl.getProduct(id);
            detailBill.setSl(1);
            detailBill.setGia(p.getGia());

            detailBill.setPk(new CTDonHangPK(p, b));
            ctDonHangDAOImpl.save(detailBill);
        } else {
            db.setSl(db.getSl() + 1);
            ctDonHangDAOImpl.update(db);
        }

        session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(b.getMaDH()));
        return "redirect:/User/home.htm";
    }

    @RequestMapping(value = "product", params = "btnBuy")
    public String addDetailBill(HttpServletRequest request, ModelMap model, HttpSession session,
            @ModelAttribute("detailBill") CTDonHang detailBill, BindingResult errors) {
        int soLuongTon = sanPhamDAOImpl.getProduct(detailBill.getPk().getSanPham().getMaSP()).getSlt();
        DonHang b = donHangDAOImpl.getBillUnBuy(((NguoiDung) session.getAttribute("customer")).getUserId());
        detailBill.getPk().setDonHang(b);
        detailBill.setGia(0L);
        CTDonHang oldBill = ctDonHangDAOImpl.getOne(CTDonHang.class, detailBill.getPk());

        if (oldBill == null) {
            //có thể k cần trường hợp này
            if (session.getAttribute("customer") == null) {
                return "redirect:/User/login.htm";
            }

            if (detailBill.getSl() > soLuongTon) {
                errors.rejectValue("sl", "detailBill", "số lượng không vượt quá SLT ("
                        + soLuongTon + ")");
            }
            if (errors.hasErrors()) {
                model.addAttribute("message", "vui lòng sửa các lỗi");
                model.addAttribute("p", sanPhamDAOImpl.getProduct(detailBill.getPk().getSanPham().getMaSP()));
                return "User/product";
            }

            ctDonHangDAOImpl.save(detailBill);
        } else {
            oldBill.setSl(oldBill.getSl() + detailBill.getSl());
            if (oldBill.getSl() > soLuongTon) {
                errors.rejectValue("sl", "detailBill", "số lượng không vượt quá SLT ("
                        + soLuongTon + ")");
            }
            if (errors.hasErrors()) {
                model.addAttribute("message", "vui lòng sửa các lỗi");
                model.addAttribute("p", sanPhamDAOImpl.getProduct(detailBill.getPk().getSanPham().getMaSP()));
                return "User/product";
            }

            ctDonHangDAOImpl.update(oldBill);
        }

        session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(b.getMaDH()));
        model.addAttribute("p", sanPhamDAOImpl.getProduct(detailBill.getPk().getSanPham().getMaSP()));
        return "User/product";

    }

    @RequestMapping(value = "payment")
    public String payment(HttpServletRequest request, HttpSession session, ModelMap model) {
        DonHang b = donHangDAOImpl.getBillUnBuy(((NguoiDung) session.getAttribute("customer")).getUserId());
        List<CTDonHang> cts = ctDonHangDAOImpl.getDetailBills(b.getMaDH());
        long sum = 0;
        for (CTDonHang ct : cts) {
            //Kiem tra slt
            SanPham s = ct.getPk().getSanPham();
            if (s.getSlt() < ct.getSl()) {
                String errorMsg = s.getTenSP() + " chỉ còn " + s.getSlt() + " sản phẩm!";
                model.addAttribute("errorMsg", errorMsg);
                showDetailBills(request, model, session);
                return "User/cart";
            }

            sum = sum + ct.getPk().getSanPham().getGia() * ct.getSl()
                    * (100 - chiTietKMDAOImpl.getDiscount(ct.getPk().getSanPham().getMaSP())) / 100;
            System.out.println(sum);
        }
        System.out.println(sum);
        b.setTongTien(sum);
        model.addAttribute("donHang", b);

        return "User/payment";
    }

    @RequestMapping(value = "payment", method = RequestMethod.POST)
    public String payment(HttpServletRequest request, HttpSession session, @ModelAttribute("donHang") DonHang donHang) {
        System.out.println("Payment");

        List<CTDonHang> cts = ctDonHangDAOImpl.getDetailBills(donHang.getMaDH());
        int i = 0;
        for (CTDonHang ct : cts) {
            ct.setGia(ct.getPk().getSanPham().getGia()
                    * (100 - chiTietKMDAOImpl.getDiscount(ct.getPk().getSanPham().getMaSP())) / 100);

            SanPham s = ct.getPk().getSanPham();
            s.setSlt(s.getSlt() - ct.getSl());

            sanPhamDAOImpl.update(s);
            ctDonHangDAOImpl.update(ct);

            i++;
        }

        donHang.setTrangThai("Chờ xác nhận");
        donHang.setNgayTao(new Date());
        donHangDAOImpl.update(donHang);

        NguoiDung k = khachHangDAOImpl.getCustomer(((NguoiDung) session.getAttribute("customer")).getTaiKhoan().getTenDN());
        int temp = donHangDAOImpl.insert(k);

        if (temp == 1) {
            session.setAttribute("detailBills", ctDonHangDAOImpl.getDetailBills(donHangDAOImpl.getBillUnBuy(k.getUserId()).getMaDH()));
        }
        return "redirect:/User/home.htm";
    }

}
