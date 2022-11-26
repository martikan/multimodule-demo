package com.martikan.carrental.carsapi.repository;

import com.martikan.carrental.carsapi.domain.Car;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Car} domain.
 */
public interface CarRepository {

    List<Car> getAll(Pageable pageable);

    Optional<Car> getById(long id);

    List<Car> search(String q, Pageable pageable);

    boolean existsBySerial(String serial);

    void updateById(long id, Car car);

    void save(Car car);

    void deleteById(long id);
}
