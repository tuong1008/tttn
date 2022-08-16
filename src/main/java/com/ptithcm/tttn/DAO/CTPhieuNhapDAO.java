/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.CTPhieuNhap;
import java.util.List;

public interface CTPhieuNhapDAO extends Dao<CTPhieuNhap>{
    List<CTPhieuNhap> getAll();
    
}
