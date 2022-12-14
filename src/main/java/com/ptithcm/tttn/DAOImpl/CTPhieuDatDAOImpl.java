/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.CTPhieuDatDAO;
import com.ptithcm.tttn.entity.CTPhieuDat;
import java.util.List;
import org.hibernate.Session;

public class CTPhieuDatDAOImpl extends AbstractDao<CTPhieuDat> implements CTPhieuDatDAO{

    @Override
    public List<CTPhieuDat> getAll() {
        return getFromQuery("FROM CTPhieuDat", CTPhieuDat.class);
    }

    @Override
    public List<CTPhieuDat> getById(String id) {
        return getFromQuery("FROM CTPhieuDat ct WHERE ct.pk.phieuDat.maPD=?", CTPhieuDat.class, id);
    }
    
    
}
