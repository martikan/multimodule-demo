package com.martikan.carrental.carsapi.repository;

import com.martikan.carrental.carsapi.domain.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation for {@link BrandRepository}.
 */
@RequiredArgsConstructor
@Repository
public class BrandRepositoryImpl implements BrandRepository {

    private static final String GET_ALL_BRANDS = "SELECT id, name FROM brands LIMIT ? OFFSET ?";

    private static final String GET_BRAND_BY_ID = "SELECT id, name FROM brands WHERE id = ? LIMIT 1";

    private static final String UPDATE_BRAND_BY_ID = "UPDATE brands set name = ? where id = ?";

    private static final String INSERT_BRAND = "INSERT INTO brands (name) VALUES (?) RETURNING id";

    private static final String DELETE_BRAND_BY_ID = "DELETE FROM brands WHERE id = ?";

    private static final String BRAND_EXISTS_BY_NAME = "SELECT EXISTS (SELECT id FROM brands WHERE LOWER(name) = LOWER(?))";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Brand> getAll(final Pageable pageable) {
        return jdbcTemplate.query(GET_ALL_BRANDS,
                this::mapRow,
                pageable.getPageSize(),
                (pageable.getPageNumber() -1) * pageable.getOffset());
    }

    @Override
    public Optional<Brand> getById(final long id) {
        return jdbcTemplate.queryForObject(GET_BRAND_BY_ID,
                (rs, rowNum) -> Optional.of(mapRow(rs, rowNum)),
                id);
    }

    @Override
    public boolean existsByName(final String name) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(BRAND_EXISTS_BY_NAME, Boolean.class, name));
    }

    @Override
    public void updateById(final long id, final Brand brand) {
        brand.setId(id);

        jdbcTemplate.update(UPDATE_BRAND_BY_ID, brand.getName(), id);
    }

    @Override
    public void save(final Brand brand) {
        final var id = jdbcTemplate.queryForObject(INSERT_BRAND, Long.class, brand.getName());

        brand.setId(Objects.requireNonNull(id));
    }

    @Override
    public void deleteById(final long id) {
        jdbcTemplate.update(DELETE_BRAND_BY_ID, id);
    }

    private Brand mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Brand.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }

}
