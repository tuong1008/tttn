package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.LoaiSP;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface LoaiSPDAO {
    ArrayList<LoaiSP> getListCategory(SessionFactory factory);
}
