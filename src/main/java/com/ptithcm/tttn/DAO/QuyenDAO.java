package com.ptithcm.tttn.DAO;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.Quyen;

public interface QuyenDAO {
	ArrayList<Quyen> getAllRole(SessionFactory factory);
	Quyen getRole(SessionFactory factory, int idRole);
}
