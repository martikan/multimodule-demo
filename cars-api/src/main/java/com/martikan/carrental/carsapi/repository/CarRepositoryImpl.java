package com.martikan.carrental.carsapi.repository;

import com.martikan.carrental.carsapi.domain.Brand;
import com.martikan.carrental.carsapi.domain.Car;
import com.martikan.carrental.carsapi.domain.ComfortLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Implementation for {@link CarRepository}.
 */
@RequiredArgsConstructor
@Repository
public class CarRepositoryImpl implements CarRepository {

    private static final String GET_ALL_CARS = "" +
            "SELECT c.id" +
            ",b.id, b.name " +
            ",c.color " +
            ",c.serial " +
            ",c.comfort " +
            ",c.available " +
            ",c.created_at " +
            "FROM cars c " +
            "INNER JOIN brands b ON b.id = c.brand_id " +
            "LIMIT ? OFFSET ?";

    private static final String SEARCH_CARS = "" +
            "SELECT c.id" +
            ",b.id, b.name " +
            ",c.color " +
            ",c.serial " +
            ",c.comfort " +
            ",c.available " +
            ",c.created_at " +
            "FROM cars c " +
            "INNER JOIN brands b ON b.id = c.brand_id " +
            "WHERE LOWER(b.name) LIKE '%' || :q || '%' OR " +
            "LOWER(c.color) LIKE '%' || :q || '%' OR " +
            "LOWER(c.serial) LIKE '%' || :q || '%' " +
            "LIMIT :limit OFFSET :offset";

    private static final String GET_CAR_BY_ID = "" +
            "SELECT c.id" +
            ",b.id, b.name " +
            ",c.color " +
            ",c.serial " +
            ",c.comfort " +
            ",c.available " +
            ",c.created_at " +
            "FROM cars c " +
            "INNER JOIN brands b ON b.id = c.brand_id " +
            "WHERE c.id = ? LIMIT 1";

    private static final String UPDATE_CAR_BY_ID = "UPDATE cars " +
            "set brand_id = ? " +
            ",color = ? " +
            ",serial = ? " +
            ",comfort = ? " +
            ",available = ? " +
            "where id = ?";

    private static final String INSERT_CAR = "INSERT INTO cars " +
            "(brand_id, color, serial, comfort, available) " +
            "VALUES (?,?,?,?,?) " +
            "RETURNING id";

    private static final String DELETE_CAR_BY_ID = "DELETE FROM cars WHERE id = ?";

    private static final String CAR_EXISTS_BY_SERIAL = "SELECT EXISTS (SELECT id FROM cars WHERE LOWER(serial) = LOWER(?))";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Car> getAll(final Pageable pageable) {
        return jdbcTemplate.query(GET_ALL_CARS,
                this::mapRow,
                pageable.getPageSize(),
                (pageable.getPageNumber() -1) * pageable.getOffset());
    }

    @Override
    public Optional<Car> getById(final long id) {
        return jdbcTemplate.queryForObject(GET_CAR_BY_ID,
                (rs, rowNum) -> Optional.of(mapRow(rs, rowNum)),
                id);
    }

    @Override
    public List<Car> search(final String q, final Pageable pageable) {
        final var params = new MapSqlParameterSource()
                .addValue("q", q.toLowerCase(Locale.ROOT))
                .addValue("limit", pageable.getPageSize())
                .addValue("offset", (pageable.getPageNumber() -1) * pageable.getOffset());

        return jdbcTemplate.query(SEARCH_CARS,
                this::mapRow,
                params);
    }

    @Override
    public boolean existsBySerial(final String serial) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(CAR_EXISTS_BY_SERIAL, Boolean.class, serial));
    }

    @Override
    public void updateById(final long id, final Car car) {
        car.setId(id);

        jdbcTemplate.update(UPDATE_CAR_BY_ID, car.getBrand().getId(), car.getColor(),
                car.getSerial(), car.getComfort().value, car.isAvailable(), id);
    }

    @Override
    public void save(final Car car) {
        final var id = jdbcTemplate.queryForObject(INSERT_CAR, Long.class,
                car.getBrand().getId(), car.getColor(),
                car.getSerial(), car.getComfort().value, car.isAvailable());

        car.setId(id);
    }

    @Override
    public void deleteById(final long id) {
        jdbcTemplate.update(DELETE_CAR_BY_ID, id);
    }

    private Car mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        return Car.builder()
                .id(rs.getLong("c.id"))
                .brand(Brand.builder().id(rs.getLong("b.id")).name(rs.getString("b.name")).build())
                .color(rs.getString("c.color"))
                .serial(rs.getString("c.serial"))
                .comfort(ComfortLevel.valueOf(rs.getString("c.comfort")))
                .available(rs.getBoolean("c.available"))
                .createdAt(ZonedDateTime.from(rs.getTimestamp("c.created_at").toLocalDateTime()))
                .build();
    }

}
