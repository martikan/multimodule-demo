package com.martikan.carrental.carsapi.controller;

import com.martikan.carrental.carsapi.dto.BrandDTO;
import com.martikan.carrental.carsapi.service.BrandService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for {@link com.martikan.carrental.carsapi.domain.Brand} domain.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands(@PageableDefault(size = 25) final Pageable pageable) {
        final var brands = brandService.getAll(pageable);
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable(name = "id") final long id) {
        final var brand = brandService.getById(id);
        return ResponseEntity.ok(brand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrandById(@PathVariable(name = "id") final long id, @Valid @RequestBody BrandDTO dto) {
        final var updatedBrand = brandService.updateById(id, dto);
        return ResponseEntity.ok(updatedBrand);
    }

    @PostMapping
    public ResponseEntity<BrandDTO> saveBrand(@Valid @RequestBody BrandDTO dto) {
        final var newBrand = brandService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrandById(@PathVariable(name = "id") final long id) {
        brandService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
