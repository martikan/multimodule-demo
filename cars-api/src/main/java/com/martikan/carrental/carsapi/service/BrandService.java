package com.martikan.carrental.carsapi.service;

import com.martikan.carrental.carsapi.domain.Brand;
import com.martikan.carrental.carsapi.dto.BrandDTO;
import com.martikan.carrental.carsapi.mapper.BrandMapper;
import com.martikan.carrental.carsapi.repository.BrandRepository;
import com.martikan.carrental.common.service.CRUD;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Services for {@link Brand} domain.
 */
@RequiredArgsConstructor
@Service
public class BrandService implements CRUD<BrandDTO> {

    private final BrandRepository brandRepository;

    private final BrandMapper mapper;

    @Override
    public List<BrandDTO> getAll(final Pageable pageable) {
        return brandRepository.getAll(pageable).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDTO> search(final String q, final Pageable pageable) {
        throw new NotImplementedException("not implemented");
    }

    @Override
    public BrandDTO getById(final long id) {
        return brandRepository.getById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "brand not found by id=" + id));
    }

    @Override
    public BrandDTO updateById(final long id, final BrandDTO dto) {
        getById(id);

        final var brand = mapper.toEntity(dto);

        brandRepository.updateById(id, brand);

        dto.setId(id);

        return dto;
    }

    @Override
    public BrandDTO save(final BrandDTO dto) {
        if (brandRepository.existsByName(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "brand name must be unique");
        }

        final var brand = mapper.toEntity(dto);

        brandRepository.save(brand);

        dto.setId(brand.getId());

        return dto;
    }

    @Override
    public void deleteById(final long id) {
        getById(id);

        brandRepository.deleteById(id);
    }
}
