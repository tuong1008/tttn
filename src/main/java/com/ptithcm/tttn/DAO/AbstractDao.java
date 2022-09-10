package com.ptithcm.tttn.DAO;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<T> implements Dao<T> {
    @Autowired
    public SessionFactory sessionFactory;

    @Override
    public String save(T t) {
        return action(t, 1);
    }

    @Override
    public String update(T t) {
        return action(t, 2);
    }

    @Override
    public String delete(T t) {
        return action(t, 3);
    }

    /**
     * @param t    object to persist in the database
     * @param crud 1 for save, 2 for update, 3 for delete
     * @return String. error result message. "" = success
     */
    @Override
    public String action(T t, int crud) {
        Transaction trans = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            trans = session.beginTransaction();

            switch (crud) {
                case 1:
                    session.saveOrUpdate(t);
                    break;
                case 2:
                    session.merge(t);
                    break;
                case 3:
                    session.delete(t);
                    break;
            }
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            e.printStackTrace();
            Throwable error = e.getCause();
            return error.getMessage();
        } finally{
            if (session != null) {
                session.close();
            }
        }
        return "";
    }


    /**
     * Get result as list from a HQL
     *
     * @param hql    hibernate query
     * @param type   type of object needs to return
     * @param params parameters
     * @return list
     */
    @Override
    public <T> List<T> getFromQuery(String hql, Class<T> type, Object... params) {
//        LOGGER.info("getFromQuery " + hql);
        List<T> list = new ArrayList<>();
        Transaction trans = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            trans = session.beginTransaction();
            Query query = session.createQuery(hql);

            setParameters(query, params);
            
            
            list.addAll(query.list());
            trans.commit();

            return list;
        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
            e.printStackTrace();
            Throwable error = e.getCause();
            System.out.println("=-=-=-=-=-=-=-"+error.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return list;
    }

    @Override
    public T getOne(Class<T> type, Object id) {
        Session session = null;
        try {
             session = sessionFactory.openSession();
            return (T) session.get(type, (Serializable) id);
        } catch (Throwable e) {
            e.printStackTrace();
//            LOGGER.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void setParameters(Query query, Object... params) {
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i, params[i]);
        }
    }

    @Override
    public String nextPK(String tenBang, String kyTuDau, String tenCotPK) {
        Integer nextID = getFromQuery("SELECT MAX(substring("+tenCotPK+", 3, LENGTH("+tenCotPK+")))+1 FROM "+ tenBang, Integer.class).get(0);
        if (nextID == null){
            return kyTuDau + "0";
        }
        return kyTuDau + nextID;
    }
}
