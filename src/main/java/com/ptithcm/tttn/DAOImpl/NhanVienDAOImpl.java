package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhanVienDAO;
import com.ptithcm.tttn.entity.NguoiDung;

import java.util.List;
import org.hibernate.Session;

public class NhanVienDAOImpl extends AbstractDao<NguoiDung> implements NhanVienDAO {
    @Override
    public NguoiDung getStaff(String username) {
        List<NguoiDung> list = getFromQuery("FROM NguoiDung n where n.taiKhoan.quyen.tenQuyen <> 'Customer' and n.taiKhoan.tenDN = ?", NguoiDung.class, username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public NguoiDung getStaffByID(String id) {
        List<NguoiDung> list = getFromQuery("FROM NguoiDung n where n.userId = ?", NguoiDung.class, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NguoiDung> getAllStaff() {
        return getFromQuery("FROM NguoiDung n where n.taiKhoan.quyen.tenQuyen <> 'Customer'", NguoiDung.class);
    }

    @Override
    public List<NguoiDung> searchAllStaff(String hoTen) {
        return getFromQuery("FROM NguoiDung n Where n.taiKhoan.quyen.tenQuyen <> 'Customer' and n.hoTen like ?", NguoiDung.class, "%" + hoTen + "%");
    }

    @Override
    public Integer getMaxNumberByName(String username) {
        List<String> list = getFromQuery("SELECT max(substring(n.tenDN,length(n.tenDN),length(n.tenDN))) FROM NguoiDung n WHERE substring(n.tenDN,1,length(n.tenDN)-1) =? and n.taiKhoan.quyen.tenQuyen <> 'Customer'", String.class);
        System.out.println(list.toString());
        return list.isEmpty() ? 0 : Integer.parseInt(list.get(0));
        
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        String hql = "SELECT max(substring(tenDN,length(tenDN),length(tenDN))) FROM NhanVien WHERE substring(tenDN,1,length(tenDN)-1) =:name";
//        Query query = session.createQuery(hql);
//        query.setParameter("name", username);
//
//        List<String> list = query.list();
//        session.getTransaction().commit();
//        session.close();
//        return list.isEmpty() ? 0 : Integer.parseInt(list.get(0));
    }

    @Override
    public List<NguoiDung> getShippers() {
        return getFromQuery("FROM NguoiDung Where taiKhoan.quyen.tenQuyen = 'Shipper' ", NguoiDung.class);
    }

}
