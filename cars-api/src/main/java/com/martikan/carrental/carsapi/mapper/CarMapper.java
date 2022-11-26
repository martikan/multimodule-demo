package com.martikan.carrental.carsapi.mapper;

import com.martikan.carrental.carsapi.domain.Car;
import com.martikan.carrental.carsapi.dto.CarDTO;
import com.martikan.carrental.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link Car} and {@link CarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarMapper extends AbstractMapper<Car, CarDTO> {
}
