package com.ptithcm.tttn.DAO;

/**
 * Xem AbstractDao.java
 * @param <T> type
 */
public interface Dao<T> {
    String save(T t);

    String update(T t);

    String delete(T t);
}
