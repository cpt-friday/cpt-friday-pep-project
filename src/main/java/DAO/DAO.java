package DAO;

import java.util.*;

public interface DAO<T> {
    T getByID(int id);
    List<T> getAll();
    void save(T t);
    void update(T t, String[] params);
    void delete(T t);
}