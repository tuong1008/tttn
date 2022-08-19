/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.CTPhieuDat;
import com.ptithcm.tttn.entity.PhieuDat;
import java.util.List;

public interface CTPhieuDatDAO extends Dao<CTPhieuDat>{
    List<CTPhieuDat> getAll();
    List<CTPhieuDat> getById(String id);
}
