package com.martikan.carrental.carsapi.dto;

import com.martikan.carrental.carsapi.domain.ComfortLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO for {@link com.martikan.carrental.carsapi.domain.Car} domain.
 */
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CarDTO {

    private Long id;

    @NotNull
    private BrandDTO brand;

    @NotBlank(message = "Color cannot be empty.")
    @Size(max = 100, message = "Color cannot be grater then 100 characters.")
    private String color;

    @NotBlank(message = "Serial cannot be empty.")
    @Size(max = 20, message = "Serial cannot be grater then 20 characters.")
    private String serial;
    
    @NotNull
    private ComfortLevel comfort;

    @NotNull
    private boolean available;

}
