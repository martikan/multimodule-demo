package com.martikan.carrental.carsapi.mapper;

import com.martikan.carrental.carsapi.domain.Brand;
import com.martikan.carrental.carsapi.dto.BrandDTO;
import com.martikan.carrental.common.mapper.AbstractMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link Brand} and {@link BrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper extends AbstractMapper<Brand, BrandDTO> {
}
