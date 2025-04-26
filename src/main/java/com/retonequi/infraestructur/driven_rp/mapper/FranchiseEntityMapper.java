package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Franchise;
import com.retonequi.infraestructur.driven_rp.entity.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FranchiseEntityMapper {
    FranchiseEntityMapper INSTANCE = Mappers.getMapper(FranchiseEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    FranchiseEntity toEntity(Franchise model);
    Franchise toModel(FranchiseEntity entity);
}
