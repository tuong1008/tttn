package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.entity.SanPham;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SanPhamDAOImpl extends AbstractDao<SanPham> implements SanPhamDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public ArrayList<SanPham> getListProduct() {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham";
        Query query = session.createQuery(hql);
        return (ArrayList<SanPham>) query.list();
    }

    @Override
    public ArrayList<SanPham> getListHotSaleProduct(int bigSaleOffPercent) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC KhuyenMaiKhung ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, bigSaleOffPercent);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListNewProduct() {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPMOI").addEntity(SanPham.class);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListHotProdduct(int monthNumber) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPHot ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, monthNumber);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListProductByName(String name) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC TimKiemSPTheoTen ?").addEntity(SanPham.class);
        sqlQuery.setString(0, name);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListProductByNameBrand(String name) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham S WHERE S.nhaCungCap.tenNCC =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        return (ArrayList<SanPham>) query.list();
    }

    @Override
    public SanPham getProduct(String maSP) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham S WHERE S.maSP =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", maSP);
        return (SanPham) query.list().get(0);
    }

}
