package com.swvalerian.crud.repository;

import java.util.List;

public interface GenericRepository<T,ID> {
    List<T> getAll();
    T getId(ID id);
    List<T> update(T t);
    T save(T t);
    void deleteById(ID id);
}
