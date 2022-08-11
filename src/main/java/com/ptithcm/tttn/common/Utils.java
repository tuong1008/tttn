package com.ptithcm.tttn.common;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ptithcm.tttn.DAO.NhanVienDAO;
import com.ptithcm.tttn.DAOImpl.NhanVienDAOImpl;

public class Utils {
	public static String createUserName(SessionFactory factory, String fullName) {
		String res="";
		String[] temp = fullName.split(" ");
		
		res=temp[temp.length-1];
		for(int i=0; i< temp.length-1 ;i++) {
			res+= temp[i].charAt(0);
		}
		int number = new NhanVienDAOImpl().getMaxNumberByName(factory, res) + 1;
		res+=number;
		return res.toUpperCase();
	}
}
