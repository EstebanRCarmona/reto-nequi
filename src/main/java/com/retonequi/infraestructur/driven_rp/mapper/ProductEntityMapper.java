package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Product;
import com.retonequi.infraestructur.driven_rp.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductEntityMapper {
    ProductEntityMapper INSTANCE = Mappers.getMapper(ProductEntityMapper.class);

    Product toModel(ProductEntity entity);
    ProductEntity toEntity(Product model);
}
