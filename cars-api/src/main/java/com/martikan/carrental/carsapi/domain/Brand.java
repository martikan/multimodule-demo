package com.martikan.carrental.carsapi.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO class for `brands` database table.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Brand {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;
}
