package com.retonequi.infraestructur.driving_http.mapper;

import com.retonequi.domain.model.responses.ProductBranchPair;
import com.retonequi.infraestructur.driving_http.dto.response.ProductWithBranchDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductWithBranchDtoMapper {
    ProductWithBranchDtoMapper INSTANCE = Mappers.getMapper(ProductWithBranchDtoMapper.class);
    ProductWithBranchDto toDto(ProductBranchPair pair);
}
