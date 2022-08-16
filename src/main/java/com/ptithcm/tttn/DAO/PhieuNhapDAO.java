/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.PhieuNhap;
import java.util.List;

public interface PhieuNhapDAO extends Dao<PhieuNhap>{
    List<PhieuNhap> getAll();
}
