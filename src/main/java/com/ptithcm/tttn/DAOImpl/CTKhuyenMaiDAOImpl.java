/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.CTKhuyenMaiDAO;
import com.ptithcm.tttn.entity.ChiTietKM;
import java.util.List;

public class CTKhuyenMaiDAOImpl extends AbstractDao<ChiTietKM> implements  CTKhuyenMaiDAO{

    @Override
    public List<ChiTietKM> getAll() {
        return getFromQuery("FROM ChiTietKM", ChiTietKM.class);
    }
    
}
