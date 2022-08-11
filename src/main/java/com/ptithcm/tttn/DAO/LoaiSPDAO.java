package com.ptithcm.tttn.DAO;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.LoaiSP;

public interface LoaiSPDAO {
	ArrayList<LoaiSP> getListCategory(SessionFactory factory);
}
