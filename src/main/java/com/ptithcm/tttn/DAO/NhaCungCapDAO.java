package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.NhaCungCap;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface NhaCungCapDAO {

    ArrayList<NhaCungCap> getSuppliers(SessionFactory factory);

}
