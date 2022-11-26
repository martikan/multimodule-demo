package com.martikan.carrental.carsapi.repository;

import com.martikan.carrental.carsapi.domain.Brand;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Brand} domain.
 */
public interface BrandRepository {

    List<Brand> getAll(Pageable pageable);

    Optional<Brand> getById(long id);

    boolean existsByName(String name);

    void updateById(long id, Brand brand);

    void save(Brand brand);

    void deleteById(long id);

}
