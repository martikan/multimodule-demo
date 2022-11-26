package com.martikan.carrental.carsapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * DTO for {@link com.martikan.carrental.carsapi.domain.Brand} domain.
 */
@Builder
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BrandDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 255, message = "Name cannot be grater then 255 characters.")
    private String name;

}
