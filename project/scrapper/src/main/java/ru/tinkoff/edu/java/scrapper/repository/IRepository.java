package ru.tinkoff.edu.java.scrapper.repository;

import java.util.List;

public interface IRepository<T> {
    void add(T object);

    void remove(T object);

    List<T> findAll();
}
