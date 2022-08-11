package com.ptithcm.tttn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

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

import com.ptithcm.tttn.DAO.KhachHangDAO;
import com.ptithcm.tttn.DAO.QuyenDAO;
import com.ptithcm.tttn.DAOImpl.KhachHangDAOImpl;
import com.ptithcm.tttn.DAOImpl.NhanVienDAOImpl;
import com.ptithcm.tttn.DAOImpl.QuyenDAOImpl;
import com.ptithcm.tttn.DAOImpl.TaiKhoanDAOImpl;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.NhanVien;
import com.ptithcm.tttn.entity.TaiKhoan;

@Transactional
@Controller
@RequestMapping("Admin/")
public class AdminController {
	
	@Autowired
	SessionFactory factory;
	
	TaiKhoanDAOImpl taiKhoanDAOImpl = new TaiKhoanDAOImpl();
	NhanVienDAOImpl nhanVienDAOImpl = new NhanVienDAOImpl();
	KhachHangDAO khachHangDAO = new KhachHangDAOImpl();
	QuyenDAOImpl quyenDAOImpl = new QuyenDAOImpl();
	
	
	@RequestMapping("login")
	public String login(HttpSession session) {
		session.removeAttribute("staff");
		return "Admin/login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, ModelMap model, HttpSession session) {
		String idAdmin = request.getParameter("name");
		String passAdmin = request.getParameter("password");
		String role = taiKhoanDAOImpl.getRole(factory, passAdmin, idAdmin);
		if ( !role.equals("") && !role.equals("customer")) {
			session.setAttribute("staff", nhanVienDAOImpl.getStaff(factory, idAdmin));
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
		Boolean flag=true;
		if(!nv.getTaiKhoan().getMatKhau().equals(oldPass)) {
			model.addAttribute("oldPassEr", "Mật khẩu cũ không chính xác");
			flag=false;
		}
		if(newPass.equals("")) {
			model.addAttribute("newPassEr", "Vui lòng nhập mật khẩu mới");
			flag=false;
		}
		if(!newPass.equals(newPassReset)) {
			model.addAttribute("newPassResetEr", "Mật khẩu nhập lại không khớp");
			flag=false;
		}
		if(!flag) {
			model.addAttribute("oldPass", oldPass);
			model.addAttribute("newPass", newPass);
			model.addAttribute("newPassReset", newPassReset);
			return "Admin/changePass";
		}
		Integer temp = taiKhoanDAOImpl.updatePass(factory, newPass, nv.getTaiKhoan().getTenDN());
		if (temp == 0) {
			model.addAttribute("message", "Thay đổi mật khẩu thất bại");
		} 
		return "Admin/info";
	}
	

	@RequestMapping("staff")
	public String staff(HttpServletRequest request, ModelMap model, HttpSession session) {

		showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));

		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("staff", new NhanVien());
		session.setAttribute("roles", quyenDAOImpl.getAllRole(factory));
		return "Admin/staff";
	}
	
	@RequestMapping(value = "staff", params = "btnSearch")
	public String searchStaff(HttpServletRequest request, ModelMap model) {
		showStaffs(request, model, nhanVienDAOImpl.searchAllStaff(factory, request.getParameter("name").trim()));
		model.addAttribute("btnStatus", "btnAdd");
		model.addAttribute("staff", new NhanVien());

		return "Admin/staff";
	}

	@RequestMapping(value = "staff", params = "btnAdd", method = RequestMethod.POST)
	public String addStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
			BindingResult errors) {
		int maQuyen = Integer.parseInt(request.getParameter("quyen"));
		if (validateStaff(request, staff, errors)) {
			TaiKhoan k = new TaiKhoan("345", "1111",quyenDAOImpl.getRole(factory, maQuyen), null, null);
			Integer temp = taiKhoanDAOImpl.insertAccount(factory, k);
			staff.setTaiKhoan(k);
			Integer temp1 = nhanVienDAOImpl.insertStaff(factory, staff);
			if (temp1 != 0) {
				model.addAttribute("message", "Thêm thành công");
				model.addAttribute("staff", new NhanVien());

			} else {
				model.addAttribute("message", "Thêm thất bại, vui lòng kiểm tra lại thông tin" + staff);
			}
		}
		model.addAttribute("btnStatus", "btnAdd");
		showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));

		return "Admin/staff";
	}

	@RequestMapping(value = "staff", params = "btnEdit", method = RequestMethod.POST)
	public String editStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
			BindingResult errors) {
		if (!validateStaff(request, staff, errors)) {
			System.out.println("Chao edit post");
			showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));
			return "Admin/staff";
		}
		System.out.println("Dia Chi: " + staff.getDiaChi());
		System.out.println("Quyen: " + staff.getTaiKhoan().getTenDN());
		Integer temp = nhanVienDAOImpl.updateStaff(factory, staff);
		if (temp != 0) {
			model.addAttribute("message", "Sửa thành công");
			model.addAttribute("staff", new NhanVien());
			model.addAttribute("btnStatus", "btnAdd");
		} else {
			model.addAttribute("message", "Sửa thất bại" + staff);
			model.addAttribute("btnStatus", "btnEdit");
		}
		showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));
		return "Admin/staff";
	}

	@RequestMapping(value = "staff/{id}.htm", params = "linkEdit")
	public String editStaff(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
		showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));
		System.out.println("Chao edit");
		model.addAttribute("btnStatus", "btnEdit");
		NhanVien s = nhanVienDAOImpl.getStaffByID(factory, id);
		model.addAttribute("staff", s);

		return "Admin/staff";
	}

	@RequestMapping(value = "staff/{id}.htm", params = "linkDelete")
	public String deleteStaff(HttpServletRequest request, ModelMap model, @ModelAttribute("staff") NhanVien staff,
			@PathVariable("id") String id) {
		NhanVien nhanVien = nhanVienDAOImpl.getStaffByID(factory, id);
		TaiKhoan taiKhoan = taiKhoanDAOImpl.getAccount(factory, nhanVien.getTaiKhoan().getTenDN());
		Integer temp = nhanVienDAOImpl.deleteStaff(factory, nhanVien);
		System.out.println("xoa toi day");
		Integer temp1 = taiKhoanDAOImpl.deleteAccount(factory, taiKhoan);
		if (temp != 0 && temp1 !=0) {
			model.addAttribute("message", "Xóa thành công");
		} else {
			model.addAttribute("message", "Xóa thất bại");
		}
		return "redirect:/Admin/staff.htm";
	}

	@RequestMapping(value = "staff/{id}.htm", params = "linkReset")
	public String resetStaff(HttpServletRequest request, ModelMap model, @PathVariable("id") String id) {
		Integer temp = taiKhoanDAOImpl.updatePass(factory, "1111", nhanVienDAOImpl.getStaffByID(factory, id).getTaiKhoan().getTenDN());
		if (temp != 0) {
			model.addAttribute("message", "Reset thành công");

		} else {
			model.addAttribute("message", "Reset thất bại");
		}

		model.addAttribute("staff", new NhanVien());
		model.addAttribute("btnStatus", "btnAdd");
		showStaffs(request, model, nhanVienDAOImpl.getAllStaff(factory));

		return "Admin/staff";
	}
	
	public Boolean validateStaff(HttpServletRequest request, NhanVien staff, BindingResult errors) {
		String checkname = "([\\p{L}\\s]+){1,50}";
		String checkphone = "[0-9]{10}";
		String checkemail = "^[A-Za-z0-9+_.-]+@(.+)$";
		String checkaddress = "([\\p{L}\\s\\d\\,]+){1,100}";

		if (staff.getHoTen().trim().matches(checkname) == false) {
			errors.rejectValue("hoTen", "staff",
					"Họ tên không được để trống, chứa ký tự đặc biệt hoặc quá 50 ký tự!");
		}
		if (staff.getSdt().trim().matches(checkphone) == false) {
			errors.rejectValue("sdt", "staff", "số điện thoại không đúng!");
		}
		if (staff.getEmail().trim().matches(checkemail) == false) {
			errors.rejectValue("email", "staff", "email không đúng định dạng!");
		}
		if (staff.getDiaChi().trim().matches(checkaddress) == false) {
			errors.rejectValue("diaChi", "staff",
					"Địa chỉ không được để trống, chứa ký tự đặc biệt hoặc quá 100 ký tự!");
		}
		if (errors.hasErrors()) {
			return false;
		}
		return true;
	}
	
	public void showStaffs(HttpServletRequest request, ModelMap model, List<NhanVien> staffs) {
		PagedListHolder pagedListHolder = new PagedListHolder(staffs);
		int page = ServletRequestUtils.getIntParameter(request, "p", 0);
		pagedListHolder.setPage(page);
		pagedListHolder.setMaxLinkedPages(5);
		pagedListHolder.setPageSize(5);
		model.addAttribute("pagedListHolder", pagedListHolder);
	}
	
	@RequestMapping("customer")
	public String customer(HttpServletRequest request, ModelMap model) {
		showCustomer(request, model,khachHangDAO.getAllCustomer(factory));
		return "Admin/customer";
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
