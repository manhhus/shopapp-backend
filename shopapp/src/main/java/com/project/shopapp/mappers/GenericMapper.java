package com.project.shopapp.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service

public class GenericMapper<D, E> {

    private final ModelMapper mapper;

    public GenericMapper() {
        this.mapper = new ModelMapper();
    }
    public E convertToEntity(D dto, Class<E> entityClass) {
        return mapper.map(dto, entityClass);
    }

    public D convertToDTO(E entity, Class<D> dtoClass) {
        return mapper.map(entity, dtoClass);
    }
}