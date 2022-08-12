package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.entity.SanPham;
import org.hibernate.*;

import java.util.ArrayList;

public class SanPhamDAOImpl implements SanPhamDAO {

    @Override
    public ArrayList<SanPham> getListProduct(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham";
        Query query = session.createQuery(hql);
        return (ArrayList<SanPham>) query.list();
    }

    @Override
    public ArrayList<SanPham> getListHotSaleProduct(SessionFactory factory, int bigSaleOffPercent) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC KhuyenMaiKhung ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, bigSaleOffPercent);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListNewProduct(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPMOI").addEntity(SanPham.class);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListHotProdduct(SessionFactory factory, int monthNumber) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPHot ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, monthNumber);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListProductByName(SessionFactory factory, String name) {
        Session session = factory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC TimKiemSPTheoTen ?").addEntity(SanPham.class);
        sqlQuery.setString(0, name);
        return (ArrayList<SanPham>) sqlQuery.list();
    }

    @Override
    public ArrayList<SanPham> getListProductByNameBrand(SessionFactory factory, String name) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham S WHERE S.nhaCungCap.tenNCC =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        return (ArrayList<SanPham>) query.list();
    }

    @Override
    public SanPham getProduct(SessionFactory factory, String maSP) {
        Session session = factory.getCurrentSession();
        String hql = "FROM SanPham S WHERE S.maSP =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", maSP);
        return (SanPham) query.list().get(0);
    }

    @Override
    public int update(SessionFactory factory, SanPham p) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(p);
            t.commit();
        } catch (Exception e) {
            System.out.println(e);
            t.rollback();
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

}
