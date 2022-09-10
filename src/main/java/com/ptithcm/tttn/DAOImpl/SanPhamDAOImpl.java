package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.ChiTietKMDAO;
import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.entity.SanPham;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SanPhamDAOImpl extends AbstractDao<SanPham> implements SanPhamDAO {
    
    @Autowired
    ChiTietKMDAO chiTietKMDAO;
    
    @Override
    public List<SanPham> getListProduct() {
        return getFromQuery("FROM SanPham", SanPham.class);
    }
    
    @Override
    public List<SanPham> getListProductToSale() {
        List<SanPham> list = getFromQuery("FROM SanPham s WHERE s.slt > 0", SanPham.class);
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list;
    }

    @Override
    public List<SanPham> getListHotSaleProduct(int bigSaleOffPercent) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        SQLQuery sqlQuery = session.createSQLQuery("EXEC KhuyenMaiKhung ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, bigSaleOffPercent);
        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        session.close();
        return list;
    }

    @Override
    public List<SanPham> getListNewProduct() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPMOI").addEntity(SanPham.class);

        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list;
    }

    @Override
    public List<SanPham> getListHotProdduct(int monthNumber) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC SPHot ?").addEntity(SanPham.class);
        sqlQuery.setInteger(0, monthNumber);

        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        session.close();
        return list;
    }

    @Override
    public List<SanPham> getListProductByNameForSale(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        SQLQuery sqlQuery = session.createSQLQuery("EXEC TimKiemSPTheoTen ?").addEntity(SanPham.class);
        sqlQuery.setString(0, name);
        List<SanPham> list = sqlQuery.list();

        session.getTransaction().commit();
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        session.close();
        return list;
    }
    
    @Override
    public List<SanPham> getListProductByName(String name) {
        return getFromQuery("FROM SanPham s WHERE s.tenSP like '%"+name+"%'", SanPham.class);
    }

    @Override
    public List<SanPham> getListProductByNameBrandForSale(String name) {
        List<SanPham> list = getFromQuery("FROM SanPham S WHERE S.nhaCungCap.tenNCC =? and S.slt > 0", SanPham.class, name);
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list;
    }
    
    @Override
    public List<SanPham> getListProductByNameBrand(String name) {
        return getFromQuery("FROM SanPham S WHERE S.nhaCungCap.tenNCC =?", SanPham.class, name);
    }

    @Override
    public List<SanPham> getListProductByIDBrand(String id) {
        List<SanPham> list = getFromQuery("FROM SanPham S WHERE S.nhaCungCap.maNCC =?", SanPham.class, id);
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list;
    }
    
    

    @Override
    public SanPham getProduct(String maSP) {
        List<SanPham> list = getFromQuery("FROM SanPham S WHERE S.maSP = ?", SanPham.class, maSP);
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SanPham> getCustomListProduct(String[] nameBrands, String fromPrice, String toPrice, String[] categories) {
        StringBuilder query = new StringBuilder("FROM SanPham s ");
        StringBuilder whereClause = new StringBuilder("WHERE s.slt>0");
        
        for (String brand : nameBrands){
            if (brand.equals("all")) break;
            if (brand.equals(nameBrands[0])){
                whereClause.append(" AND ");
            }
            
            whereClause.append("s.nhaCungCap.maNCC = '"+brand+"'");
            if (!brand.equals(nameBrands[nameBrands.length-1])){
                whereClause.append(" OR ");
            }
        }
        
        if (!fromPrice.equals("")){
            whereClause.append(" AND s.gia>="+fromPrice);
        }
        
        if (!toPrice.equals("")){
            whereClause.append(" AND s.gia<="+toPrice);
        }
        
        for (String category : categories){
            if (category.equals("all")) break;
            
            if (category.equals(categories[0])){
                whereClause.append(" AND ");
            }
            
            whereClause.append("s.loaiSP.maLoai = '"+category+"'");
            if (!category.equals(categories[categories.length-1])){
                whereClause.append(" OR ");
            }
        }        
        
        query.append(whereClause);
        
        List<SanPham> list = getFromQuery(query.toString(), SanPham.class);
        for (SanPham sp : list){
            sp.setGiamGiaLonNhat(chiTietKMDAO.getDiscount(sp.getMaSP()));
        }
        return list;
    }

}
