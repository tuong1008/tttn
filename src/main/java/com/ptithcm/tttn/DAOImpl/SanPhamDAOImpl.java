package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.entity.SanPham;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SanPhamDAOImpl extends AbstractDao<SanPham> implements SanPhamDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public List<SanPham> getListProduct() {
        return getFromQuery("FROM SanPham", SanPham.class);
    }

    @Override
    public List<SanPham> getListHotSaleProduct(int bigSaleOffPercent) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        SQLQuery sqlQuery = session.createSQLQuery("EXEC KhuyenMaiKhung ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, bigSaleOffPercent);
        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<SanPham> getListNewProduct() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPMOI").addEntity(SanPham.class);

        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<SanPham> getListHotProdduct(int monthNumber) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPHot ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, monthNumber);

        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<SanPham> getListProductByName(String name) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        SQLQuery sqlQuery = session.createSQLQuery("EXEC TimKiemSPTheoTen ?").addEntity(SanPham.class);
        sqlQuery.setString(0, name);
        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        return list;
    }

    @Override
    public List<SanPham> getListProductByNameBrand(String name) {
        return getFromQuery("FROM SanPham S WHERE S.nhaCungCap.tenNCC =?", SanPham.class, name);
    }

    @Override
    public SanPham getProduct(String maSP) {
        List<SanPham> list = getFromQuery("FROM SanPham S WHERE S.maSP = ?", SanPham.class, maSP);
        return list.isEmpty() ? null : list.get(0);
    }

}
