package com.ptithcm.tttn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptithcm.tttn.DAO.*;
import com.ptithcm.tttn.common.Utils;
import com.ptithcm.tttn.entity.CTDonHang;
import com.ptithcm.tttn.entity.CTPhieuDat;
import com.ptithcm.tttn.entity.CTPhieuDatPK;
import com.ptithcm.tttn.entity.CTPhieuNhap;
import com.ptithcm.tttn.entity.CTPhieuNhapPK;
import com.ptithcm.tttn.entity.ChiTietKM;
import com.ptithcm.tttn.entity.ChiTietKMPK;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.HoaDon;
import com.ptithcm.tttn.entity.KhuyenMai;
import com.ptithcm.tttn.entity.LoaiSP;
import com.ptithcm.tttn.entity.NhaCungCap;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.entity.PhieuDat;
import com.ptithcm.tttn.entity.PhieuNhap;
import com.ptithcm.tttn.entity.Quyen;
import com.ptithcm.tttn.entity.SanPham;
import com.ptithcm.tttn.entity.TaiKhoan;
import com.ptithcm.tttn.model.Revenue;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Controller
@RequestMapping("Admin/")
public class AdminController {

    @Autowired
    TaiKhoanDAO taiKhoanDAOImpl;

    @Autowired
    KhachHangDAO khachHangDAO;

    @Autowired
    ChiTietKMDAO chiTietKMDAO;

    @Autowired
    LoaiSPDAO loaiSPDAO;

    @Autowired
    NhanVienDAO nhanVienDAO;

    @Autowired
    QuyenDAO quyenDAO;

    @Autowired
    NhaCungCapDAO nhaCungCapDAO;

    @Autowired
    SanPhamDAO sanPhamDAO;

    @Autowired
    PhieuDatDAO phieuDatDAO;

    @Autowired
    CTPhieuDatDAO ctPhieuDatDAO;

    @Autowired
    PhieuNhapDAO phieuNhapDAO;

    @Autowired
    CTPhieuNhapDAO ctPhieuNhapDAO;

    @Autowired
    KhuyenMaiDAO khuyenMaiDAO;

    @Autowired
    DonHangDAO donHangDAO;

    @Autowired
    CTDonHangDAO ctDonHangDAO;

    @Autowired
    HoaDonDAO hoaDonDAO;

    @RequestMapping("login")
    public String login(HttpSession session) {
        session.removeAttribute("staff");
        return "Admin/login";

    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, ModelMap model, HttpSession session) {
        String idAdmin = request.getParameter("name");
        String passAdmin = request.getParameter("password");
        String role = taiKhoanDAOImpl.getRole(passAdmin, idAdmin);
        if (!role.equals("") && !role.equals("Customer")) {
            session.setAttribute("staff", nhanVienDAO.getStaff(idAdmin));
            return "redirect:/Admin/info.htm";
        } else {
            model.addAttribute("message", "Đăng nhập thất bại, vui lòng điền đúng thông tin đăng nhập!");
        }
        return "Admin/login";
    }

    @RequestMapping("info")
    public String info(HttpSession session, ModelMap model) {
        return "Admin/info";
    }
//BEGIN-----------Staff

    @RequestMapping(value = "changePass")
    public String editStaffPass() {
        return "Admin/changePass";
    }

    @RequestMapping(value = "changePass", params = "btnUpdatePass", method = RequestMethod.POST)
    public String editStaffPass(HttpServletRequest request, HttpSession session, ModelMap model) {
        String newPass = request.getParameter("newPass");
        String oldPass = request.getParameter("oldPass");
        String newPassReset = request.getParameter("newPassReset");
        NguoiDung nv = ((NguoiDung) session.getAttribute("staff"));
        Boolean flag = true;
        if (!nv.getTaiKhoan().getMatKhau().equals(oldPass)) {
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
            return "Admin/changePass";
        }
        Integer temp = taiKhoanDAOImpl.updatePass(newPass, nv.getTaiKhoan().getTenDN());
        if (temp == 0) {
            model.addAttribute("message", "Thay đổi mật khẩu thất bại");
        }
        return "Admin/info";
    }

    @RequestMapping("staff")
    public String staff(HttpServletRequest request, ModelMap model, HttpSession session) {

        showStaffs(request, model, nhanVienDAO.getAllStaff());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("staff", new NguoiDung());
        session.setAttribute("roles", quyenDAO.getAllRole());
        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnSearch")
    public String searchStaff(HttpServletRequest request, ModelMap model) {
        showStaffs(request, model, nhanVienDAO.searchAllStaff(request.getParameter("name").trim()));
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("staff", new NguoiDung());

        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnAdd", method = RequestMethod.POST)
    public String addStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NguoiDung staff,
            BindingResult errors) {
        int maQuyen = Integer.parseInt(request.getParameter("quyen"));
        if (validateStaff(request, staff, errors)) {
            TaiKhoan k = new TaiKhoan(createUserName(staff.getHoTen()), "1111",
                    quyenDAO.getOne(Quyen.class, maQuyen));
            System.out.println(k.getTenDN());
            String result1 = taiKhoanDAOImpl.save(k);
            staff.setTaiKhoan(k);
            staff.setUserId(nhanVienDAO.nextPK("NguoiDung", "NV", "userId"));
            String result2 = nhanVienDAO.save(staff);
            if (result2.equals("")) {
                model.addAttribute("message", "Thêm thành công");
                model.addAttribute("staff", new NguoiDung());

            } else {
                model.addAttribute("message", result2);
            }
        }
        model.addAttribute("btnStatus", "btnAdd");
        showStaffs(request, model, nhanVienDAO.getAllStaff());

        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnEdit", method = RequestMethod.POST)
    public String editStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NguoiDung staff,
            BindingResult errors) {
        int maQuyen = Integer.parseInt(request.getParameter("quyen"));
        if (!validateStaff(request, staff, errors)) {
            System.out.println("Chao edit post");
            showStaffs(request, model, nhanVienDAO.getAllStaff());
            return "Admin/staff";
        }
        TaiKhoan account = taiKhoanDAOImpl.getAccount(staff.getTaiKhoan().getTenDN());
        account.setQuyen(quyenDAO.getRole(maQuyen));
        String temp0 = taiKhoanDAOImpl.update(account);
        String temp = nhanVienDAO.update(staff);
        if (temp.equals("")) {
            model.addAttribute("message", "Sửa thành công");
            model.addAttribute("staff", new NguoiDung());
            model.addAttribute("btnStatus", "btnAdd");
        } else {
            model.addAttribute("message", "Sửa thất bại" + staff);
            model.addAttribute("btnStatus", "btnEdit");
        }
        showStaffs(request, model, nhanVienDAO.getAllStaff());
        return "Admin/staff";
    }

    @RequestMapping(value = "staff/{id}.htm", params = "linkEdit")
    public String editStaff(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        showStaffs(request, model, nhanVienDAO.getAllStaff());
        System.out.println("Chao edit");
        model.addAttribute("btnStatus", "btnEdit");
        NguoiDung s = nhanVienDAO.getStaffByID(id);
        model.addAttribute("staff", s);

        return "Admin/staff";
    }

    @RequestMapping(value = "staff/{id}.htm", params = "linkDelete")
    public String deleteStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NguoiDung staff,
            @PathVariable("id") String id) {
        NguoiDung nhanVien = nhanVienDAO.getStaffByID(id);
        TaiKhoan taiKhoan = taiKhoanDAOImpl.getAccount(nhanVien.getTaiKhoan().getTenDN());

        String temp = nhanVienDAO.delete(nhanVien);

        System.out.println("xoa toi day");

        String temp1 = taiKhoanDAOImpl.delete(taiKhoan);

        if (temp.isEmpty() && temp1.isEmpty()) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return "redirect:/Admin/staff.htm";
    }

    @RequestMapping(value = "staff/{id}.htm", params = "linkReset")
    public String resetStaff(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        Integer temp = taiKhoanDAOImpl.updatePass("1111", nhanVienDAO.getStaffByID(id).getTaiKhoan().getTenDN());
        if (temp != 0) {
            model.addAttribute("message", "Reset thành công");

        } else {
            model.addAttribute("message", "Reset thất bại");
        }

        model.addAttribute("staff", new NguoiDung());
        model.addAttribute("btnStatus", "btnAdd");
        showStaffs(request, model, nhanVienDAO.getAllStaff());

        return "Admin/staff";
    }

    public Boolean validateStaff(HttpServletRequest request, NguoiDung staff, BindingResult errors) {
        String checkname = "([\\p{L}\\s]+){1,50}";
        String checkphone = "[0-9]{10}";
        String checkemail = "^[A-Za-z0-9+_.-]+@(.+)$";
        String checkaddress = "([\\p{L}\\s\\d\\,]+){1,100}";

        if (!staff.getHoTen().trim().matches(checkname)) {
            errors.rejectValue("hoTen", "staff",
                    "Họ tên không được để trống, chứa ký tự đặc biệt hoặc quá 50 ký tự!");
        }
        if (!staff.getSdt().trim().matches(checkphone)) {
            errors.rejectValue("sdt", "staff", "số điện thoại không đúng!");
        }
        if (!staff.getEmail().trim().matches(checkemail)) {
            errors.rejectValue("email", "staff", "email không đúng định dạng!");
        }
        if (!staff.getDiaChi().trim().matches(checkaddress)) {
            errors.rejectValue("diaChi", "staff",
                    "Địa chỉ không được để trống, chứa ký tự đặc biệt hoặc quá 100 ký tự!");
        }
        return !errors.hasErrors();
    }

    public void showStaffs(HttpServletRequest request, ModelMap model, List<NguoiDung> staffs) {
        PagedListHolder pagedListHolder = new PagedListHolder(staffs);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

    public String createUserName(String fullName) {
        fullName = Utils.covertToString(fullName);
        String res = "";
        String[] temp = fullName.split(" ");

        res = temp[temp.length - 1];
        for (int i = 0; i < temp.length - 1; i++) {
            res += temp[i].charAt(0);
        }
        int number = nhanVienDAO.getMaxNumberByName(res) + 1;
        res += number;
        return res.toUpperCase();
    }
//END-----------Staff

//BEGIN---------Customer
    @RequestMapping("customer")
    public String customer(HttpServletRequest request, ModelMap model) {
        showCustomer(request, model, khachHangDAO.getAllCustomer());
        return "Admin/customer";
    }

    @RequestMapping(value = "customer", params = "btnSearch")
    public String searchCustomer(HttpServletRequest request, ModelMap model) {
        showCustomer(request, model, khachHangDAO.searchCustomers(request.getParameter("name")));
        return "Admin/customer";
    }

    @RequestMapping(value = "customer/{id}.htm", params = "linkBlock")
    public String blockCustomer(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        if (khachHangDAO.setStatus(0, id) == 1) {
            model.addAttribute("message", "Block thành công");
        }
        showCustomer(request, model, khachHangDAO.getAllCustomer());
        return "redirect:/Admin/customer.htm";
    }

    @RequestMapping(value = "customer/{id}.htm", params = "linkUnBlock")
    public String unBlockCustomer(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        if (khachHangDAO.setStatus(1, id) == 1) {
            model.addAttribute("message", "UnBlock thành công");
        }
        showCustomer(request, model, khachHangDAO.getAllCustomer());
        return "redirect:/Admin/customer.htm";
    }

    public void showCustomer(HttpServletRequest request, ModelMap model, List<NguoiDung> customers) {
        PagedListHolder pagedListHolder = new PagedListHolder(customers);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(10);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-----------Customer

//BEGIN-------------Product Management
    @RequestMapping("productList")
    public String productList(HttpServletRequest request, ModelMap model) {
        showProducts(request, model, sanPhamDAO.getListProduct());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        model.addAttribute("product", new SanPham());
        return "Admin/product";
    }

    @RequestMapping(value = "productList", params = "btnSearch")
    public String SearchProductList(HttpServletRequest request, ModelMap model) {
        String name = request.getParameter("name");
        showProducts(request, model, sanPhamDAO.getListProductByName(name));
        model.addAttribute("name", name);
        
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        model.addAttribute("product", new SanPham());
        return "Admin/product";
    }

    @RequestMapping(value = "product/{id}.htm", params = "linkEdit")
    public String editProduct(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        showProducts(request, model, sanPhamDAO.getListProduct());

        model.addAttribute("btnStatus", "btnEdit");
        SanPham s = sanPhamDAO.getOne(SanPham.class, id);
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        model.addAttribute("product", s);

        return "Admin/product";
    }

    @RequestMapping(value = "product", params = "btnEdit", method = RequestMethod.POST)
    public String editProduct(HttpServletRequest request, ModelMap model, @ModelAttribute("product") SanPham sp,
            BindingResult errors, @RequestParam("hinhAnh") MultipartFile productImage) {

        SanPham old = sanPhamDAO.getOne(SanPham.class, sp.getMaSP());
        String extension = ""; //đuôi file
        System.out.println(productImage);
        if (productImage.getSize() != 0) {
            String fileName = productImage.getOriginalFilename();

            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                extension = fileName.substring(index);
            }

            sp.setHinhAnh(sp.getMaSP() + extension);
        } else {
            sp.setHinhAnh(old.getHinhAnh());
        }
        sp.setGia(old.getGia());
        sp.setSlt(old.getSlt());

        String temp = sanPhamDAO.update(sp);

        if (temp.isEmpty()) {
            model.addAttribute("message", "Sửa thành công");
            model.addAttribute("product", new SanPham());
            model.addAttribute("btnStatus", "btnAdd");
            String rootDir = request.getSession().getServletContext().getRealPath("/");
            Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "img" + File.separator + "imgProduct" + File.separator + sp.getMaSP() + extension);
            if (productImage != null && !productImage.isEmpty()) {
                try {
                    productImage.transferTo(new File(path.toString()));
                    //System.out.println("IMage Save at:"+path.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Product image saving failed", e);
                }
            }
        } else {
            model.addAttribute("message", "Sửa thất bại" + sp);
            model.addAttribute("btnStatus", "btnEdit");
        }
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        showProducts(request, model, sanPhamDAO.getListProduct());

        return "Admin/product";
    }

    @RequestMapping(value = "product", params = "btnAdd", method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request, @RequestParam("hinhAnh") MultipartFile productImage,
            @RequestParam("loaiSP.maLoai") String loaiSPId,
            @RequestParam("nhaCungCap.maNCC") String nccId,
            ModelMap model, @ModelAttribute("product") SanPham sp,
            BindingResult errors) {
        sp.setSpMoi(1);
        sp.setLoaiSP(loaiSPDAO.getOne(LoaiSP.class, loaiSPId));
        sp.setNhaCungCap(nhaCungCapDAO.getOne(NhaCungCap.class, nccId));

        String fileName = productImage.getOriginalFilename();
        System.out.println(fileName);
        String extension = ""; //đuôi file

        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            extension = fileName.substring(index);
        }
        sp.setGia(0);
        sp.setSlt(0);
        sp.setMaSP(sanPhamDAO.nextPK("SanPham", "SP", "maSP"));
        sp.setHinhAnh(sp.getMaSP() + extension);
        String temp1 = sanPhamDAO.save(sp);

        if (temp1.isEmpty()) {
            model.addAttribute("message", "Thêm thành công");
            model.addAttribute("product", new SanPham());

            String rootDir = request.getSession().getServletContext().getRealPath("/");
            Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "img" + File.separator + "imgProduct" + File.separator + sp.getMaSP() + extension);
            if (productImage != null && !productImage.isEmpty()) {
                try {
                    productImage.transferTo(new File(path.toString()));
                    //System.out.println("IMage Save at:"+path.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Product image saving failed", e);
                }
            }
        } else {
            model.addAttribute("message", "Thêm thất bại, vui lòng kiểm tra lại thông tin" + sp);
        }
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        showProducts(request, model, sanPhamDAO.getListProduct());

        return "Admin/product";
    }

    @RequestMapping(value = "product/{id}.htm", params = "linkDelete")
    public String deleteStaff(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {

        SanPham sp = sanPhamDAO.getOne(SanPham.class, id);
        String result = sanPhamDAO.delete(sp);
        if (result.equals("")) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", result);
        }
        return "redirect:/Admin/productList.htm";
    }

    public void showProducts(HttpServletRequest request, ModelMap model, List<SanPham> products) {
        PagedListHolder pagedListHolder = new PagedListHolder(products);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-------------Product Management    
//BEGIN-----------Product Type

    @RequestMapping("product-type")
    public String getProductTypePage(HttpServletRequest request, ModelMap model, HttpSession session) {

        showProductTypes(request, model, loaiSPDAO.getListCategory());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("productType", new LoaiSP());
        return "Admin/productType";
    }

    @RequestMapping(value = "product-type", params = "btnSearch")
    public String searchProductType(HttpServletRequest request, ModelMap model) {
        showProductTypes(request, model, loaiSPDAO.searchByName(request.getParameter("name").trim()));
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("productType", new LoaiSP());

        return "Admin/productType";
    }

    @RequestMapping(value = "product-type", params = "btnAdd", method = RequestMethod.POST)
    public String addProductType(HttpServletRequest request, ModelMap model, @ModelAttribute("productType") LoaiSP type,
            BindingResult errors) {

        type.setMaLoai(loaiSPDAO.nextPK("LoaiSP", "LP", "maLoai"));
        String temp1 = loaiSPDAO.save(type);

        if (temp1.isEmpty()) {
            model.addAttribute("message", "Thêm thành công");
            model.addAttribute("productType", new LoaiSP());
        } else {
            model.addAttribute("message", "Thêm thất bại, vui lòng kiểm tra lại thông tin" + type);
        }
        model.addAttribute("btnStatus", "btnAdd");
        showProductTypes(request, model, loaiSPDAO.getListCategory());

        return "Admin/productType";
    }

    @RequestMapping(value = "product-type", params = "btnEdit", method = RequestMethod.POST)
    public String editProductType(HttpServletRequest request, ModelMap model, @ModelAttribute("productType") LoaiSP type,
            BindingResult errors) {

        String temp = loaiSPDAO.update(type);

        if (temp.isEmpty()) {
            model.addAttribute("message", "Sửa thành công");
            model.addAttribute("productType", new LoaiSP());
            model.addAttribute("btnStatus", "btnAdd");
        } else {
            model.addAttribute("message", "Sửa thất bại" + type);
            model.addAttribute("btnStatus", "btnEdit");
        }
        showProductTypes(request, model, loaiSPDAO.getListCategory());
        return "Admin/productType";
    }

    @RequestMapping(value = "product-type/{id}.htm", params = "linkEdit")
    public String editProductType(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        showProductTypes(request, model, loaiSPDAO.getListCategory());
        System.out.println("Chao edit");
        model.addAttribute("btnStatus", "btnEdit");
        LoaiSP s = loaiSPDAO.getOne(LoaiSP.class, id);
        model.addAttribute("productType", s);

        return "Admin/productType";
    }

    @RequestMapping(value = "product-type/{id}.htm", params = "linkDelete")
    public String deleteProductType(HttpServletRequest request, ModelMap model,
            @PathVariable("id") String id) {
        LoaiSP type = loaiSPDAO.getOne(LoaiSP.class, id);

        String temp = loaiSPDAO.delete(type);

        System.out.println("xoa toi day");

        if (temp.isEmpty()) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return "redirect:/Admin/productType.htm";
    }

    public void showProductTypes(HttpServletRequest request, ModelMap model, List<LoaiSP> types) {
        PagedListHolder pagedListHolder = new PagedListHolder(types);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-------------Product Type
//BEGIN-------------Order

    @RequestMapping("order")
    public String getOrderPage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        return "Admin/order";
    }

    @RequestMapping(value = "order", params = "btnAdd", method = RequestMethod.POST)
    public String addOrder(HttpServletRequest request, ModelMap model, HttpSession session) {
        String[] sanPhams = request.getParameterValues("sanPham");
        String[] soLuongs = request.getParameterValues("soLuong");
        String[] gias = request.getParameterValues("gia");
        String nccId = request.getParameter("nhaCungCap");

        PhieuDat p = new PhieuDat();
        p.setMaPD(phieuDatDAO.nextPK("PhieuDat", "PD", "maPD"));
        p.setNgayTao(new Date());
        p.setNhaCungCap(nhaCungCapDAO.getOne(NhaCungCap.class, nccId));
        p.setNhanVien((NguoiDung) session.getAttribute("staff"));
        phieuDatDAO.save(p);

        for (int i = 0; i < sanPhams.length; i++) {
            CTPhieuDatPK pk = new CTPhieuDatPK();
            pk.setPhieuDat(p);
            pk.setSanPham(sanPhamDAO.getOne(SanPham.class, sanPhams[i]));

            CTPhieuDat ct = new CTPhieuDat();
            ct.setPk(pk);
            ct.setGia(Long.valueOf(gias[i]));
            ct.setSl(Integer.valueOf(soLuongs[i]));

            ctPhieuDatDAO.save(ct);
        }
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        return "Admin/order";
    }

    @RequestMapping(value = "order", params = "btnSearch")
    public String searchOrder(HttpServletRequest request, ModelMap model) {
        showOrderMng(request, model, phieuDatDAO.searchAllPhieuDat(request.getParameter("name").trim()));
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("productType", new LoaiSP());

        return "Admin/productType";
    }

    @RequestMapping("orderMng")
    public String getOrderMngPage(HttpServletRequest request, ModelMap model) {
        showOrderMng(request, model, phieuDatDAO.getAll());
        return "Admin/orderMng";
    }

    public void showOrderMng(HttpServletRequest request, ModelMap model, List<PhieuDat> phieuDats) {
        PagedListHolder pagedListHolder = new PagedListHolder(phieuDats);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-------------Order
//BEGIN-------------Promotion

    @RequestMapping("promotionList")
    public String promotionList(HttpServletRequest request, ModelMap model) {
        showPromotion(request, model, khuyenMaiDAO.getAll());
        return "Admin/promotionList";
    }

    @RequestMapping(value = "promotionList", params = "btnSearch")
    public String SearchPromotionList(HttpServletRequest request, ModelMap model) {
        showPromotion(request, model, khuyenMaiDAO.getListByMota(request.getParameter("name")));
        return "Admin/promotionList";
    }

    @RequestMapping("promotionDetail/{id}.htm")
    public String promotionDetailList(HttpServletRequest request, ModelMap model, @PathVariable("id") Integer id) {
        model.addAttribute("maKM", id);
        model.addAttribute("btnStatus", "btnAdd");
        List<ChiTietKM> ctKhuyenMai = chiTietKMDAO.getDetailPromotions(id);
        List<SanPham> products = sanPhamDAO.getListProduct();
        for (ChiTietKM ct : ctKhuyenMai) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getMaSP().equals(ct.getPk().getSanPham().getMaSP())) {
                    products.remove(i);
                    break;
                }
            }
        }

        showPromotionDetail(request, model, ctKhuyenMai);
        model.addAttribute("dsSanPham", products);

        ChiTietKMPK newCTKey = new ChiTietKMPK(khuyenMaiDAO.getOne(KhuyenMai.class, id), new SanPham());
        ChiTietKM newCT = new ChiTietKM();
        newCT.setPk(newCTKey);

        model.addAttribute("ct", newCT);
        return "Admin/promotionDetail";
    }

    @RequestMapping(value = "promotionDetail/{id}.htm", method = RequestMethod.POST, params = "btnAdd")
    public String addPromotionDetail(HttpServletRequest request, @PathVariable("id") Integer id, @ModelAttribute("ct") ChiTietKM ct, ModelMap model) {
        chiTietKMDAO.save(ct);
        return "redirect:/Admin/promotionDetail/" + id + ".htm";
    }

    @RequestMapping(value = "promotionDetail/{id}.htm", method = RequestMethod.POST, params = "btnEdit")
    public String editPromotionDetail(HttpServletRequest request, @PathVariable("id") Integer id, @ModelAttribute("ct") ChiTietKM ct, ModelMap model) {
        chiTietKMDAO.update(ct);
        return "redirect:/Admin/promotionDetail/" + id + ".htm";
    }

    @RequestMapping(value = "promotionDetail.htm", params = "linkEdit")
    public String loadEditPromotionDetail(HttpServletRequest request, ModelMap model) {
        String maKM = request.getParameter("maKM");
        String maSP = request.getParameter("maSP");

        ChiTietKMPK key = new ChiTietKMPK(khuyenMaiDAO.getOne(KhuyenMai.class, Integer.valueOf(maKM)), sanPhamDAO.getOne(SanPham.class, maSP));
        ChiTietKM chiTiet = chiTietKMDAO.getOne(ChiTietKM.class, key);
        model.addAttribute("ct", chiTiet);
        model.addAttribute("btnStatus", "btnEdit");

        List<ChiTietKM> ctKhuyenMai = chiTietKMDAO.getDetailPromotions(Integer.valueOf(maKM));
        List<SanPham> products = sanPhamDAO.getListProduct();
        for (ChiTietKM ct : ctKhuyenMai) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getMaSP().equals(ct.getPk().getSanPham().getMaSP())) {
                    products.remove(i);
                    break;
                }
            }
        }

        showPromotionDetail(request, model, ctKhuyenMai);
        model.addAttribute("dsSanPham", products);
        model.addAttribute("maKM", maKM);
        return "Admin/promotionDetail";
    }

    @RequestMapping(value = "promotionDetail.htm", params = "linkDelete")
    public String deletePromotionDetail(HttpServletRequest request, ModelMap model) {
        String maKM = request.getParameter("maKM");
        String maSP = request.getParameter("maSP");
        ChiTietKMPK key = new ChiTietKMPK(khuyenMaiDAO.getOne(KhuyenMai.class, Integer.valueOf(maKM)),
                sanPhamDAO.getOne(SanPham.class, maSP));
        chiTietKMDAO.delete(chiTietKMDAO.getOne(ChiTietKM.class, key));

        return "redirect:/Admin/promotionDetail/" + maKM + ".htm";
    }

    @RequestMapping("promotion")
    public String getPromotionPage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("dsSanPham", sanPhamDAO.getListProduct());
        return "Admin/promotion";
    }

    @RequestMapping(value = "promotion", params = "btnAdd", method = RequestMethod.POST)
    public String addPromotion(HttpServletRequest request, ModelMap model, HttpSession session) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String ngayBD = request.getParameter("ngayBD");
        String ngayKT = request.getParameter("ngayKT");
        String moTa = request.getParameter("moTa");
        String[] maSPs = request.getParameterValues("maSP");
        String[] giamGias = request.getParameterValues("giamGia");

        KhuyenMai p = new KhuyenMai();
        p.setNgayBD(formatter.parse(ngayBD));
        p.setNgayKT(formatter.parse(ngayKT));
        p.setMoTa(moTa);
        p.setNhanVien((NguoiDung) session.getAttribute("staff"));

        khuyenMaiDAO.save(p);

        for (int i = 0; i < maSPs.length; i++) {
            ChiTietKMPK pk = new ChiTietKMPK();
            pk.setKhuyenMai(p);
            pk.setSanPham(sanPhamDAO.getOne(SanPham.class, maSPs[i]));

            ChiTietKM ct = new ChiTietKM();

            ct.setPk(pk);
            ct.setGiamGia(Integer.valueOf(giamGias[i]));

            chiTietKMDAO.save(ct);
        }
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("dsSanPham", sanPhamDAO.getListProduct());
        return "Admin/promotion";
    }

    @RequestMapping(value = "promotion/{id}.htm", params = "btnEdit", method = RequestMethod.POST)
    public String editPromotion(HttpServletRequest request, ModelMap model, HttpSession session, @PathVariable("id") Integer id) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String ngayBD = request.getParameter("ngayBD");
        String ngayKT = request.getParameter("ngayKT");
        String moTa = request.getParameter("moTa");

        KhuyenMai p = khuyenMaiDAO.getOne(KhuyenMai.class, id);
        p.setNgayBD(formatter.parse(ngayBD));
        p.setNgayKT(formatter.parse(ngayKT));
        p.setMoTa(moTa);
        p.setNhanVien((NguoiDung) session.getAttribute("staff"));

        khuyenMaiDAO.update(p);

        return "redirect:/Admin/promotionList.htm";
    }

    @RequestMapping(value = "promotion/{id}.htm", params = "linkEdit")
    public String editPromotion(HttpServletRequest request, ModelMap model, @PathVariable("id") Integer id) {

        KhuyenMai km = khuyenMaiDAO.getOne(KhuyenMai.class, id);
        model.addAttribute("maKM", id);
        model.addAttribute("ngayBD", km.getNgayBD());
        model.addAttribute("ngayKT", km.getNgayKT());
        model.addAttribute("moTa", km.getMoTa());
        model.addAttribute("btnStatus", "btnEdit");

        return "Admin/promotionEdit";
    }

    @RequestMapping(value = "promotion/{id}.htm", params = "linkDelete")
    public String deletePromotion(HttpServletRequest request, ModelMap model, @PathVariable("id") Integer id) {
        KhuyenMai km = khuyenMaiDAO.getOne(KhuyenMai.class, id);
        String result = khuyenMaiDAO.delete(km);
        if (result.equals("")) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", result);
        }
        showPromotion(request, model, khuyenMaiDAO.getAll());
        return "Admin/promotionList";
    }

    public void showPromotionDetail(HttpServletRequest request, ModelMap model, List<ChiTietKM> promotions) {
        PagedListHolder pagedListHolder = new PagedListHolder(promotions);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(10);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

    public void showPromotion(HttpServletRequest request, ModelMap model, List<KhuyenMai> promotions) {
        PagedListHolder pagedListHolder = new PagedListHolder(promotions);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(10);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-------------Promotion    
//BEGIN-------------Import

    @RequestMapping("import/{id}.htm")
    public String getImportPage(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("ctPhieuDat", ctPhieuDatDAO.getById(id));
        model.addAttribute("maPD", id);
        return "Admin/import";
    }

    @RequestMapping(value = "import/{id}.htm", params = "btnAdd", method = RequestMethod.POST)
    public String addImport(HttpServletRequest request, ModelMap model, @PathVariable("id") String id, HttpSession session) {
        String[] maSPs = request.getParameterValues("maSP");
        String[] soLuongs = request.getParameterValues("sl");
        String[] gias = request.getParameterValues("gia");
        long tongTien = 0;
        for (int i = 0; i < soLuongs.length; i++) {
            tongTien = tongTien + Long.valueOf(soLuongs[i]) * Long.valueOf(gias[i]);
        }

        PhieuNhap p = new PhieuNhap();
        p.setMaPN(phieuDatDAO.nextPK("PhieuNhap", "PN", "maPN"));
        p.setNgayTao(new Date());
        p.setTongTien(tongTien);
        p.setPhieuDat(phieuDatDAO.getOne(PhieuDat.class, id));
        p.setNhanVien((NguoiDung) session.getAttribute("staff"));
        phieuNhapDAO.save(p);

        for (int i = 0; i < soLuongs.length; i++) {
            SanPham sp = sanPhamDAO.getOne(SanPham.class, maSPs[i]);
            CTPhieuNhapPK pk = new CTPhieuNhapPK();
            pk.setPhieuNhap(p);
            pk.setSanPham(sp);

            CTPhieuNhap ct = new CTPhieuNhap();
            ct.setPk(pk);
            ct.setGia(Long.valueOf(gias[i]));
            ct.setSl(Integer.valueOf(soLuongs[i]));

            ctPhieuNhapDAO.save(ct);

            if (!soLuongs[i].equals("0")) {
                sp.setGia(Long.valueOf(gias[i]) * 12 / 10);
            }
            sp.setSlt(Integer.valueOf(soLuongs[i]) + sp.getSlt());
            sanPhamDAO.update(sp);
        }
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        return "Admin/import";
    }
//END-------------Import

//BEGIN-------------Supplier
    @RequestMapping("supplier")
    public String supplier(HttpServletRequest request, ModelMap model, HttpSession session) {
        showSuppliers(request, model, nhaCungCapDAO.getSuppliers());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("supplier", new NhaCungCap());
        return "Admin/supplier";
    }
    
    @RequestMapping(value = "supplier", params = "btnSearch")
	public String searchSupplier(HttpServletRequest request, ModelMap model) {
		String name = request.getParameter("name");
		showSuppliers(request, model, nhaCungCapDAO.searchAllSuppliers(name));
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("supplier", new NhaCungCap());
		model.addAttribute("name", name);
		return "Admin/supplier";
	}

    @RequestMapping(value = "supplier", params = "btnAdd", method = RequestMethod.POST)
    public String addSupplier(HttpServletRequest request, ModelMap model, @ModelAttribute("supplier") NhaCungCap ncc,
            BindingResult errors) {
        ncc.setMaNCC(nhaCungCapDAO.nextPK("NhaCungCap", "CC", "maNCC"));
        String temp1 = nhaCungCapDAO.save(ncc);

        if (temp1.isEmpty()) {
            model.addAttribute("message", "Thêm thành công");
            model.addAttribute("staff", new NhaCungCap());
        } else {
            model.addAttribute("message", temp1);
        }
        model.addAttribute("btnStatus", "btnAdd");
        showSuppliers(request, model, nhaCungCapDAO.getSuppliers());

        return "Admin/supplier";
    }

    @RequestMapping(value = "supplier", params = "btnEdit", method = RequestMethod.POST)
    public String editSupplier(HttpServletRequest request, ModelMap model,
            @ModelAttribute("supplier") NhaCungCap supplier, BindingResult errors) {
        if (!validateSuppiler(request, supplier, errors)) {
            System.out.println("Chao edit post");
            showSuppliers(request, model, nhaCungCapDAO.getSuppliers());
            return "Admin/supplier";
        }
        String result = nhaCungCapDAO.update(supplier);
        if (result.equals("")) {
            model.addAttribute("message", "Sửa thành công");
            model.addAttribute("supplier", new NhaCungCap());
            model.addAttribute("btnStatus", "btnAdd");
        } else {
            model.addAttribute("message", "Sửa thất bại" + supplier);
            model.addAttribute("btnStatus", "btnEdit");
        }
        showSuppliers(request, model, nhaCungCapDAO.getSuppliers());
        return "Admin/supplier";
    }

    @RequestMapping(value = "supplier/{id}.htm", params = "linkEdit")
    public String editSupplier(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        showSuppliers(request, model, nhaCungCapDAO.getSuppliers());
        System.out.println("Chao edit");
        model.addAttribute("btnStatus", "btnEdit");
        NhaCungCap supplier = nhaCungCapDAO.getOne(NhaCungCap.class, id);
        model.addAttribute("supplier", supplier);
        return "Admin/supplier";
    }

    @RequestMapping(value = "supplier/{id}.htm", params = "linkDelete")
    public String deleteSupplier(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        NhaCungCap supplier = nhaCungCapDAO.getOne(NhaCungCap.class, id);
        String result = nhaCungCapDAO.delete(supplier);
        System.out.println("xin chao xoa");
        if (result.equals("")) {
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return "redirect:/Admin/supplier.htm";
    }

    public void showSuppliers(HttpServletRequest request, ModelMap model, List<NhaCungCap> suppliers) {
        PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

    public Boolean validateSuppiler(HttpServletRequest request, NhaCungCap supplier, BindingResult errors) {
        String checkname = "([\\p{L}\\s]+){1,50}";
        String checkphone = "[0-9]{10,13}";
        String checkemail = "^[A-Za-z0-9+_.-]+@(.+)$";
        String checkaddress = "([\\p{L}\\s\\d\\,]+){1,200}";

        if (supplier.getTenNCC().trim().matches(checkname) == false) {
            errors.rejectValue("tenNCC", "supplier",
                    "Họ tên không được để trống, chứa ký tự đặc biệt hoặc quá 50 ký tự!");
        }
        if (supplier.getSdt().trim().matches(checkphone) == false) {
            errors.rejectValue("sdt", "supplier", "số điện thoại không đúng!");
        }
        if (supplier.getEmail().trim().matches(checkemail) == false) {
            errors.rejectValue("email", "supplier", "email không đúng định dạng!");
        }
        if (supplier.getDiaChi().trim().matches(checkaddress) == false) {
            errors.rejectValue("diaChi", "supplier",
                    "Địa chỉ không được để trống, chứa ký tự đặc biệt hoặc quá 200 ký tự!");
        }
        if (errors.hasErrors()) {
            return false;
        }
        return true;
    }
//END-------------Supplier

    @RequestMapping("product-supplier")
    public void getProductsOfSupplier(HttpServletRequest request, ModelMap model, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String maNCC = request.getParameter("maNCC");
        List<SanPham> products = sanPhamDAO.getListProductByIDBrand(maNCC);

        mapper.writeValue(response.getOutputStream(), products);
    }

//BEGIN----------------- bill manager ---------------------//
    @RequestMapping("billUnConfirm")
    public String billUnConfirm(HttpServletRequest request, ModelMap model) {
        List<DonHang> bills = donHangDAO.getBillUnconfirm();
        Collections.sort(bills);
        showBill(request, model, bills);
        model.addAttribute("nameBill", "billUnConfirm");
        return "Admin/bill";
    }

    @RequestMapping("billCancel")
    public String billCancel(HttpServletRequest request, ModelMap model, HttpSession session) {
        List<DonHang> bills = donHangDAO.getBillCancel();
        Collections.sort(bills);
        showBill(request, model, bills);
        model.addAttribute("nameBill", "billCancel");
        return "Admin/bill";
    }

    @RequestMapping("billComplete")
    public String billComplete(HttpServletRequest request, ModelMap model, HttpSession session) {
        List<DonHang> bills = donHangDAO.getBillComplete(session);
        Collections.sort(bills);
        showBill(request, model, bills);
        model.addAttribute("nameBill", "billComplete");
        return "Admin/bill";
    }

    @RequestMapping("billDelivery")
    public String billDelivery(HttpServletRequest request, ModelMap model, HttpSession session) {
        List<DonHang> bills = donHangDAO.getBillDelivering(session);
        Collections.sort(bills);
        showBill(request, model, bills);
        model.addAttribute("nameBill", "billDelivery");
        return "Admin/bill";
    }

    @RequestMapping(value = "billUnConfirm", params = "btnSearch")
    public String searchBillUnConfirm(HttpServletRequest request, ModelMap model) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        showBill(request, model, donHangDAO.searchBillUnconfirm(from, to));
        model.addAttribute("nameBill", "billUnConfirm");
        model.addAttribute("from", "from");
        model.addAttribute("to", "to");
        return "Admin/bill";
    }

    @RequestMapping(value = "billComplete", params = "btnSearch")
    public String searchBillComplete(HttpServletRequest request, ModelMap model, HttpSession session) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        showBill(request, model, donHangDAO.searchBillComplete(session, from, to));
        model.addAttribute("nameBill", "billComplete");
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        return "Admin/bill";
    }

    @RequestMapping(value = "billCancel", params = "btnSearch")
    public String searchbillCancel(HttpServletRequest request, ModelMap model) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        showBill(request, model, donHangDAO.searchBillCancel(from, to));
        model.addAttribute("nameBill", "billCancel");
        model.addAttribute("from", "from");
        model.addAttribute("to", "to");
        return "Admin/bill";
    }

    public void showBill(HttpServletRequest request, ModelMap model, List<DonHang> bills) {
        PagedListHolder pagedListHolder = new PagedListHolder(bills);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(10);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

    @RequestMapping(value = "billDetail/{id}.htm")
    public String billDetail(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        List<CTDonHang> ctDonHangs = ctDonHangDAO.getDetailBills(id);
        model.addAttribute("list", ctDonHangs);
        model.addAttribute("listNV", nhanVienDAO.getShippers());
        model.addAttribute("bill", donHangDAO.getOne(DonHang.class, id));
        return "Admin/billDetail";
    }

    @RequestMapping(value = "billDetail/{id}.htm", params = "btnBrower")
    public String billDetailBrower(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id) {
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNhanVienG(nhanVienDAO.getOne(NguoiDung.class, request.getParameter("maNVG")));
        bill.setNhanVienD((NguoiDung) session.getAttribute("staff"));
        donHangDAO.update(bill);
        return "redirect:/Admin/billUnConfirm.htm";
    }

    @RequestMapping(value = "billDetail/{id}.htm", params = "btnCancel")
    public String billDetailCancel(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id) {
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNhanVienD((NguoiDung) session.getAttribute("staff"));
        bill.setTrangThai(-1);
        donHangDAO.update(bill);
        return "redirect:/Admin/billUnConfirm.htm";
    }

    @RequestMapping(value = "billDetail/{id}.htm", params = "btnConfirm")
    public String billDetailConfirm(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id) {
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNgayNhan(new Date());
        bill.setTrangThai(2);
        donHangDAO.update(bill);
        NguoiDung staff = (NguoiDung) session.getAttribute("staff");
        HoaDon billl = new HoaDon(hoaDonDAO.nextPK("HoaDon", "HD", "maHD"), new Date(), Utils.getMST(), staff, bill, null);
        hoaDonDAO.save(billl);
        return "redirect:/Admin/billDelivery.htm";
    }
//END----------------bill manager

//BEGIN statistic
    @RequestMapping(value = "statistic")
    public String statistic() {
        return "Admin/statistic";
    }

    @RequestMapping(value = "statistic", params = "btnSearch")
    public String statistic(HttpServletRequest request, ModelMap model) {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        List<Revenue> list = getRevenue(from, to);
        model.addAttribute("list", list);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        Integer sum = 0;
        for (Revenue r : list) {
            sum = sum + r.getTotal().intValue();
        }
        model.addAttribute("sum", sum);
        return "Admin/statistic";
    }

    public List<Revenue> getRevenue(String from, String to) {
        List<Revenue> revenues = donHangDAO.revenue(from, to);
        Integer start = Integer.parseInt(from.substring(5, 7));
        Integer end = Integer.parseInt(to.substring(5, 7));
        List<Revenue> list = new ArrayList<Revenue>();
        Integer yearFrom = Integer.parseInt(from.substring(0, 4));
        Integer yearTo = Integer.parseInt(to.substring(0, 4));
        for (int i = yearFrom; i <= yearTo; i++) {
            int s = 1, e = 12;
            if (i == yearFrom) {
                s = start;
            }
            if (i == yearTo) {
                e = end;
            }
            Boolean flag = false;
            for (Revenue r : revenues) {
                if (r.getYear() == i) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                for (int j = s; j <= e; j++) {
                    Boolean f = false;
                    for (Revenue r : revenues) {
                        if (r.getYear() == i && r.getMonth() == j) {
                            f = true;
                            list.add(new Revenue(i, j, r.getTotal()));
                            break;
                        }
                    }
                    if (!f) {
                        list.add(new Revenue(i, j, 0));
                    }
                }
            } else {
                list.add(new Revenue(i, 0, 0));
            }
        }
        return list;
    }
//END statistic
}
