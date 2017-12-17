package com.elorating.service;

import java.util.List;

public interface GenericService<T> {

    T getById(String id);
    List<T> getAll();
    T save(T t);
    void deleteById(String id);
}
