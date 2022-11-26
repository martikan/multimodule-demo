package com.martikan.carrental.common.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Blueprint for CRUD services.
 * FIXME: should be in COMMON lib.
 * @param <T> DTO
 */
public interface CRUD<T> {

    List<T> getAll(Pageable pageable);

    List<T> search(String q, Pageable pageable);

    T getById(long id);

    T updateById(long id, T dto);

    T save(T dto);

    void deleteById(long id);
}