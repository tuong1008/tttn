package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.QuyenDAO;
import com.ptithcm.tttn.entity.Quyen;

import java.util.List;

public class QuyenDAOImpl extends AbstractDao<Quyen> implements QuyenDAO {

    @Override
    public List<Quyen> getAllRole() {
        return getFromQuery("FROM Quyen", Quyen.class);
    }

    @Override
    public Quyen getRole(int idRole) {
        List<Quyen> list = getFromQuery("FROM Quyen q Where q.maQuyen = ?", Quyen.class, idRole);
        return list.isEmpty() ? null : list.get(0);
    }
}
