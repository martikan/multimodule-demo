package com.martikan.carrental.carsapi.service;

import com.martikan.carrental.carsapi.domain.Car;
import com.martikan.carrental.carsapi.dto.CarDTO;
import com.martikan.carrental.carsapi.mapper.CarMapper;
import com.martikan.carrental.carsapi.repository.CarRepository;
import com.martikan.carrental.common.service.CRUD;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Services for {@link Car} domain.
 */
@RequiredArgsConstructor
@Service
public class CarService implements CRUD<CarDTO> {

    private final CarRepository carRepository;

    private final CarMapper mapper;

    @Override
    public List<CarDTO> getAll(final Pageable pageable) {
        return carRepository.getAll(pageable).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> search(final String q, final Pageable pageable) {
        return carRepository.search(q, pageable).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO getById(final long id) {
        return carRepository.getById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "car not found by id=" + id));
    }

    @Override
    public CarDTO updateById(final long id, final CarDTO dto) {
        getById(id);

        final var car = mapper.toEntity(dto);

        carRepository.updateById(id, car);

        dto.setId(id);

        return dto;
    }

    @Override
    public CarDTO save(final CarDTO dto) {
        if (carRepository.existsBySerial(dto.getSerial())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "serial must be unique");
        }

        final var car = mapper.toEntity(dto);

        carRepository.save(car);

        dto.setId(car.getId());

        return dto;
    }

    @Override
    public void deleteById(final long id) {
        carRepository.deleteById(id);
    }
}
