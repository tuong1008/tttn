package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhanVienDAO;
import com.ptithcm.tttn.entity.NhanVien;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NhanVienDAOImpl extends AbstractDao<NhanVien> implements NhanVienDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public NhanVien getStaff(String username) {
        List<NhanVien> list = getFromQuery("FROM NhanVien n where n.taiKhoan.tenDN = ?", NhanVien.class, username);
        return list.isEmpty() ? null : list.get(0);
    }

    public NhanVien getStaffByID(String id) {
        List<NhanVien> list = getFromQuery("FROM NhanVien n where n.maNV = ?", NhanVien.class, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<NhanVien> getAllStaff() {
        return getFromQuery("FROM NhanVien", NhanVien.class);
    }

    @Override
    public List<NhanVien> searchAllStaff(String hoTen) {
        return getFromQuery("FROM NhanVien n Where n.hoTen like ?", NhanVien.class, "%" + hoTen + "%");
    }

    @Override
    public Integer getMaxNumberByName(String username) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        String hql = "SELECT max(substring(tenDN,length(tenDN),length(tenDN))) FROM NhanVien WHERE substring(tenDN,1,length(tenDN)-1) =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", username);

        List<String> list = query.list();
        session.getTransaction().commit();

        return list.isEmpty() ? 0 : Integer.parseInt(list.get(0));
    }

}
