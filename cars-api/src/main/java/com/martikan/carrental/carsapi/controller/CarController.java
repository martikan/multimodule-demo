package com.martikan.carrental.carsapi.controller;

import com.martikan.carrental.carsapi.dto.CarDTO;
import com.martikan.carrental.carsapi.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for {@link com.martikan.carrental.carsapi.domain.Car} domain.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars(@PageableDefault(size = 25) final Pageable pageable) {
        final var cars = carService.getAll(pageable);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCars(@PageableDefault(size = 25) final Pageable pageable,
                                                   @RequestParam(name = "q") final String q) {
        final var cars = carService.search(q, pageable);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable(name = "id") final long id) {
        final var car = carService.getById(id);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCarById(@PathVariable(name = "id") final long id, @Valid @RequestBody CarDTO dto) {
        final var updatedCar = carService.updateById(id, dto);
        return ResponseEntity.ok(updatedCar);
    }

    @PostMapping
    public ResponseEntity<CarDTO> saveCar(@Valid @RequestBody CarDTO dto) {
        final var newCar = carService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable(name = "id") final long id) {
        carService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
