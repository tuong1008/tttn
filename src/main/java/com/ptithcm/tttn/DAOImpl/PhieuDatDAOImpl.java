/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.PhieuDatDAO;
import com.ptithcm.tttn.entity.NhanVien;
import com.ptithcm.tttn.entity.PhieuDat;
import java.util.List;

public class PhieuDatDAOImpl extends AbstractDao<PhieuDat> implements PhieuDatDAO{

    @Override
    public List<PhieuDat> getAll() {
        return getFromQuery("FROM PhieuDat", PhieuDat.class);
    }

    @Override
    public List<PhieuDat> searchAllPhieuDat(String search) {
        return getFromQuery("FROM PhieuDat n Where n.ngayTao LIKE ?", PhieuDat.class, "%" + search + "%");
    }
    
}
