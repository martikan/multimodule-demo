package com.martikan.carrental.carsapi.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

/**
 * POJO class for `cars` database table.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Car {

    @EqualsAndHashCode.Include
    private Long id;

    private Brand brand;

    private String color;

    private String serial;

    private ComfortLevel comfort;

    private boolean available;

    private ZonedDateTime createdAt;
}
