package com.ptithcm.tttn.DAO;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.NhaCungCap;

public interface NhaCungCapDAO {
	
	ArrayList<NhaCungCap> getSuppliers(SessionFactory factory);
	
}
