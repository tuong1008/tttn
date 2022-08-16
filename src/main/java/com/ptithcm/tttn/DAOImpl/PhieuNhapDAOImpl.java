/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.PhieuNhapDAO;
import com.ptithcm.tttn.entity.PhieuNhap;
import java.util.List;

public class PhieuNhapDAOImpl extends AbstractDao<PhieuNhap> implements  PhieuNhapDAO{

    @Override
    public List<PhieuNhap> getAll() {
        return getFromQuery("FROM PhieuNhap", PhieuNhap.class);
    }
    
}
