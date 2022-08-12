package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.Quyen;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface QuyenDAO {
    ArrayList<Quyen> getAllRole(SessionFactory factory);

    Quyen getRole(SessionFactory factory, int idRole);
}
