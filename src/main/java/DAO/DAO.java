package DAO;

import java.util.*;

public interface DAO<T> {
    List<T> getAll();
    T getByID(int id);
    T save(T t);
    void update(int id, T t);
    void delete(T t);
}