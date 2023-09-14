package DAO;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getByID(int id);
    T save(T t);
    void update(int id, T t);
    void delete(int id);
}