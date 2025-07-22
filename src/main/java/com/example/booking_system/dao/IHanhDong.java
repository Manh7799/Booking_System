package com.example.booking_system.dao;

import java.util.List;

public interface IHanhDong<T, idT> {

    List<T> getList();

    T getById(idT id);

    boolean add(T obj);

    boolean update(T obj);

    boolean delete(idT id);
}