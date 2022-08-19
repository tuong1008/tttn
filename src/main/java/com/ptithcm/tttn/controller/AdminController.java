package com.ptithcm.tttn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptithcm.tttn.DAO.*;
import com.ptithcm.tttn.entity.CTPhieuDat;
import com.ptithcm.tttn.entity.CTPhieuDatPK;
import com.ptithcm.tttn.entity.CTPhieuNhap;
import com.ptithcm.tttn.entity.CTPhieuNhapPK;
import com.ptithcm.tttn.entity.ChiTietKM;
import com.ptithcm.tttn.entity.ChiTietKMPK;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.KhuyenMai;
import com.ptithcm.tttn.entity.LoaiSP;
import com.ptithcm.tttn.entity.NhaCungCap;
import com.ptithcm.tttn.entity.NhanVien;
import com.ptithcm.tttn.entity.PhieuDat;
import com.ptithcm.tttn.entity.PhieuNhap;
import com.ptithcm.tttn.entity.SanPham;
import com.ptithcm.tttn.entity.TaiKhoan;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Controller
@RequestMapping("Admin/")
public class AdminController {

    @Autowired
    SessionFactory factory;

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
        if (!role.equals("") && !role.equals("customer")) {
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
        NhanVien nv = ((NhanVien) session.getAttribute("staff"));
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
        model.addAttribute("staff", new NhanVien());
        session.setAttribute("roles", quyenDAO.getAllRole());
        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnSearch")
    public String searchStaff(HttpServletRequest request, ModelMap model) {
        showStaffs(request, model, nhanVienDAO.searchAllStaff(request.getParameter("name").trim()));
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("staff", new NhanVien());

        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnAdd", method = RequestMethod.POST)
    public String addStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
            BindingResult errors) {
        int maQuyen = Integer.parseInt(request.getParameter("quyen"));
        if (validateStaff(request, staff, errors)) {
            TaiKhoan k = new TaiKhoan("345", "1111", quyenDAO.getRole(maQuyen), null, null);

            String temp = taiKhoanDAOImpl.save(k);

            staff.setTaiKhoan(k);

            String temp1 = nhanVienDAO.save(staff);

            if (temp1.isEmpty()) {
                model.addAttribute("message", "Thêm thành công");
                model.addAttribute("staff", new NhanVien());
            } else {
                model.addAttribute("message", "Thêm thất bại, vui lòng kiểm tra lại thông tin" + staff);
            }
        }
        model.addAttribute("btnStatus", "btnAdd");
        showStaffs(request, model, nhanVienDAO.getAllStaff());

        return "Admin/staff";
    }

    @RequestMapping(value = "staff", params = "btnEdit", method = RequestMethod.POST)
    public String editStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
            BindingResult errors) {
        if (!validateStaff(request, staff, errors)) {
            System.out.println("Chao edit post");
            showStaffs(request, model, nhanVienDAO.getAllStaff());
            return "Admin/staff";
        }
        System.out.println("Dia Chi: " + staff.getDiaChi());
        System.out.println("Quyen: " + staff.getTaiKhoan().getTenDN());
        String temp = nhanVienDAO.update(staff);

        if (temp.isEmpty()) {
            model.addAttribute("message", "Sửa thành công");
            model.addAttribute("staff", new NhanVien());
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
        NhanVien s = nhanVienDAO.getStaffByID(id);
        model.addAttribute("staff", s);

        return "Admin/staff";
    }

    @RequestMapping(value = "staff/{id}.htm", params = "linkDelete")
    public String deleteStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
            @PathVariable("id") String id) {
        NhanVien nhanVien = nhanVienDAO.getStaffByID(id);
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

        model.addAttribute("staff", new NhanVien());
        model.addAttribute("btnStatus", "btnAdd");
        showStaffs(request, model, nhanVienDAO.getAllStaff());

        return "Admin/staff";
    }

    public Boolean validateStaff(HttpServletRequest request, NhanVien staff, BindingResult errors) {
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

    public void showStaffs(HttpServletRequest request, ModelMap model, List<NhanVien> staffs) {
        PagedListHolder pagedListHolder = new PagedListHolder(staffs);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-----------Staff

//BEGIN-------------Product Management
    @RequestMapping("product")
    public String product(HttpServletRequest request, ModelMap model, HttpSession session) {

        showProducts(request, model, sanPhamDAO.getListProduct());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("categories", loaiSPDAO.getListCategory());
        model.addAttribute("suppliers", nhaCungCapDAO.getSuppliers());
        model.addAttribute("product", new SanPham());
        return "Admin/product";
    }

    @RequestMapping(value = "product", params = "btnAdd", method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request, @RequestParam("hinhAnh") MultipartFile productImage,
            @RequestParam("loaiSP") String loaiSPId,
            @RequestParam("nhaCungCap") String nccId,
            ModelMap model, @ModelAttribute("product") SanPham sp,
            BindingResult errors) {
        sp.setLoaiSP(loaiSPDAO.getOne(LoaiSP.class, loaiSPId));
        sp.setNhaCungCap(nhaCungCapDAO.getOne(NhaCungCap.class, nccId));
        String temp1 = sanPhamDAO.save(sp);

        if (temp1.isEmpty()) {
            model.addAttribute("message", "Thêm thành công");
            model.addAttribute("product", new SanPham());

            String fileName = productImage.getOriginalFilename();
            System.out.println(fileName);
            String extension = ""; //đuôi file

            int index = fileName.lastIndexOf(".");
            if (index > 0) {
                extension = fileName.substring(index);
            }

            String rootDir = request.getSession().getServletContext().getRealPath("/");
            Path path = Paths.get(rootDir+"WEB-INF"+ File.separator + "resource" + File.separator + "img" + File.separator + "imgProduct" + File.separator + sp.getMaSP() + extension);
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
    public String addOrder(HttpServletRequest request, ModelMap model) {
        String[] sanPhams = request.getParameterValues("sanPham");
        String[] soLuongs = request.getParameterValues("soLuong");
        String[] gias = request.getParameterValues("gia");
        String nccId = request.getParameter("nhaCungCap");
        
        PhieuDat p = new PhieuDat();
        p.setMaPD(phieuDatDAO.nextPK("PhieuDat", "PD", "maPD"));
        p.setNgayTao(new Date());
        p.setNhaCungCap(nhaCungCapDAO.getOne(NhaCungCap.class, nccId));
        phieuDatDAO.save(p);
        
        for (int i=0 ; i<sanPhams.length ; i++){
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
    @RequestMapping("promotion")
    public String getPromotionPage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("dsSanPham", sanPhamDAO.getListProduct());
        return "Admin/promotion";
    }

    @RequestMapping(value = "promotion", params = "btnAdd", method = RequestMethod.POST)
    public String addPromotion(HttpServletRequest request, ModelMap model) throws ParseException {
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
        
        khuyenMaiDAO.save(p);
        
        for (int i=0 ; i<maSPs.length ; i++){
            ChiTietKMPK pk = new ChiTietKMPK();
            pk.setKhuyenMai(p);
            pk.setSanPham(sanPhamDAO.getOne(SanPham.class, maSPs[i]));
            
            ChiTietKM ct = new ChiTietKM();
            
            ct.setChiTietKMPK(pk);
            ct.setGiamGia(Integer.valueOf(giamGias[i]));
            
            chiTietKMDAO.save(ct);
        }
        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("dsSanPham", sanPhamDAO.getListProduct());
        return "Admin/promotion";
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
    public String addImport(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
        String[] maSPs = request.getParameterValues("maSP");
        String[] soLuongs = request.getParameterValues("sl");
        String[] gias = request.getParameterValues("gia");
        long tongTien=0;
        for (int i=0 ; i<soLuongs.length ; i++){
            tongTien = tongTien + Long.valueOf(soLuongs[i])*Long.valueOf(gias[i]);
        }
        
        
        PhieuNhap p = new PhieuNhap();
        p.setMaPN(phieuDatDAO.nextPK("PhieuNhap", "PN", "maPN"));
        p.setNgayTao(new Date());
        p.setTongTien(tongTien);
        p.setPhieuDat(phieuDatDAO.getOne(PhieuDat.class, id));
        phieuNhapDAO.save(p);
        
        for (int i=0 ; i<soLuongs.length ; i++){
            SanPham sp = sanPhamDAO.getOne(SanPham.class, maSPs[i]);
            CTPhieuNhapPK pk = new CTPhieuNhapPK();
            pk.setPhieuNhap(p);
            pk.setSanPham(sp);
            
            CTPhieuNhap ct = new CTPhieuNhap();
            ct.setPk(pk);
            ct.setGia(Long.valueOf(gias[i]));
            ct.setSl(Integer.valueOf(soLuongs[i]));
            
            ctPhieuNhapDAO.save(ct);
            
            if (soLuongs[i].equals("0")) sp.setGia(Long.valueOf(gias[i]) * 12 / 10);
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
        showSupplier(request, model, nhaCungCapDAO.getSuppliers());

        model.addAttribute("btnStatus", "btnAdd");
        model.addAttribute("supplier", new NhaCungCap());
        return "Admin/supplier";
    }

    @RequestMapping(value = "supplier", params = "btnAdd", method = RequestMethod.POST)
    public String addSupplier(HttpServletRequest request, ModelMap model, @ModelAttribute("supplier") NhaCungCap ncc,
            BindingResult errors) {
//        ncc.setMaNCC(nhaCungCapDAO.nextPK("NhaCungCap", "CC", "maNCC"));
        String temp1 = nhaCungCapDAO.save(ncc);

        if (temp1.isEmpty()) {
            model.addAttribute("message", "Thêm thành công");
            model.addAttribute("staff", new NhaCungCap());
        } else {
            model.addAttribute("message", "Thêm thất bại, vui lòng kiểm tra lại thông tin" + ncc);
        }
        model.addAttribute("btnStatus", "btnAdd");
        showSupplier(request, model, nhaCungCapDAO.getSuppliers());

        return "Admin/supplier";
    }

    public void showSupplier(HttpServletRequest request, ModelMap model, List<NhaCungCap> suppliers) {
        PagedListHolder pagedListHolder = new PagedListHolder(suppliers);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(5);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }
//END-------------Supplier
    
    @RequestMapping("customer")
    public String customer(HttpServletRequest request, ModelMap model) {
        showCustomer(request, model, khachHangDAO.getAllCustomer(factory));
        return "Admin/customer";
    }

    @RequestMapping("product-supplier")
    public void getProductsOfSupplier(HttpServletRequest request, ModelMap model, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String maNCC = request.getParameter("maNCC");
        List<SanPham> products = sanPhamDAO.getListProductByIDBrand(maNCC);
        
        mapper.writeValue(response.getOutputStream(), products);
    }

    public void showCustomer(HttpServletRequest request, ModelMap model, List<KhachHang> customers) {
        PagedListHolder pagedListHolder = new PagedListHolder(customers);
        int page = ServletRequestUtils.getIntParameter(request, "p", 0);
        pagedListHolder.setPage(page);
        pagedListHolder.setMaxLinkedPages(5);
        pagedListHolder.setPageSize(10);
        model.addAttribute("pagedListHolder", pagedListHolder);
    }

}
