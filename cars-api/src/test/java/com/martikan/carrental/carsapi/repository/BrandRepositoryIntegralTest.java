package com.martikan.carrental.carsapi.repository;

import com.martikan.carrental.carsapi.CarsApiApplicationTest;
import com.martikan.carrental.carsapi.domain.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BrandRepositoryIntegralTest extends CarsApiApplicationTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void truncData() {
        jdbcTemplate.update("DELETE FROM brands");
    }

    @Test
    void getAllTest_Successfully() {

        final var brandsForSave = new LinkedHashMap<Long, Brand>();

        for (int i = 0; i < 3; i++) {
            final var b = getRandomBrandForSave();
            brandRepository.save(b);
            brandsForSave.put(b.getId(), b);
        }

        final Pageable pageable = PageRequest.of(1, 20);

        final var brands = brandRepository.getAll(pageable);

        brands.forEach(b -> {
            assertNotNull(b.getId());

            final var o = brandsForSave.get(b.getId());

            assertEquals(o.getName(), b.getName());
        });
    }

    @Test
    void saveTest_SaveSuccessfully() {
        final var brand = getRandomBrandForSave();
        assertNull(brand.getId()); // The initial id must be null

        brandRepository.save(brand);

        assertNotNull(brand.getId());
        assertEquals(brand.getName(), brand.getName());
    }

    private Brand getRandomBrandForSave() {
        return Brand.builder()
                .name(super.getRandomString())
                .build();
    }

}
