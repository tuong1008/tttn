/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.KhuyenMaiDAO;
import com.ptithcm.tttn.entity.KhuyenMai;
import java.util.List;

public class KhuyenMaiDAOImpl extends AbstractDao<KhuyenMai> implements  KhuyenMaiDAO{

    @Override
    public List<KhuyenMai> getAll() {
        return getFromQuery("FROM KhuyenMai", KhuyenMai.class);
    }

}
