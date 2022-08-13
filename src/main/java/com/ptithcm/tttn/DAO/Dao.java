package com.ptithcm.tttn.DAO;

public interface Dao<T> {
    String save(T t);

    String update(T t);

    String delete(T t);
}
