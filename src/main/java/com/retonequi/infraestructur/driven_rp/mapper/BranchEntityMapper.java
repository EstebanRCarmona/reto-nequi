package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Branch;
import com.retonequi.infraestructur.driven_rp.entity.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BranchEntityMapper {
    BranchEntityMapper INSTANCE = Mappers.getMapper(BranchEntityMapper.class);

    Branch toModel(BranchEntity entity);
    BranchEntity toEntity(Branch model);
}
