package com.ptithcm.tttn.DAOImpl;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ptithcm.tttn.DAO.NhaCungCapDAO;
import com.ptithcm.tttn.entity.NhaCungCap;
import com.ptithcm.tttn.entity.SanPham;

public class NhaCungCapDAOImpl implements NhaCungCapDAO{
	
	public ArrayList<NhaCungCap> getSuppliers(SessionFactory factory){
		Session session = factory.getCurrentSession();
		String hql = "FROM NhaCungCap";
		Query query = session.createQuery(hql);
		return (ArrayList<NhaCungCap>) query.list();
	};
}
