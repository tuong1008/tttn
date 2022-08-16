/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.CTPhieuNhapDAO;
import com.ptithcm.tttn.entity.CTPhieuNhap;
import java.util.List;

public class CTPhieuNhapDAOImpl extends AbstractDao<CTPhieuNhap> implements CTPhieuNhapDAO{

    @Override
    public List<CTPhieuNhap> getAll() {
        return getFromQuery("FROM CTPhieuNhap", CTPhieuNhap.class);
    }
    
}
