package com.ptithcm.tttn.DAO;

import java.util.List;
import org.hibernate.Query;

/**
 * Xem AbstractDao.java
 * @param <T> type
 */
public interface Dao<T> {
    String save(T t);

    String update(T t);

    String delete(T t);
    
    public String action(T t, int crud);


    /**
     * Get result as list from a HQL
     *
     * @param hql    hibernate query
     * @param type   type of object needs to return
     * @param params parameters
     * @return list
     */
    public <T> List<T> getFromQuery(String hql, Class<T> type, Object... params);

    public T getOne(Class<T> type, Object id);

    public void setParameters(Query query, Object... params);
    
    public String nextPK(String tenBang, String kyTuDau, String tenCotPK);
}
