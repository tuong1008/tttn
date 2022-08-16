/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.KhuyenMai;
import java.util.List;

public interface KhuyenMaiDAO extends Dao<KhuyenMai>{
    List<KhuyenMai> getAll();
}
