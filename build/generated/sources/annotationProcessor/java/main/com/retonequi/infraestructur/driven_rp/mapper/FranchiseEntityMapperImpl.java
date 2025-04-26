package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Franchise;
import com.retonequi.infraestructur.driven_rp.entity.FranchiseEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T14:06:00-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class FranchiseEntityMapperImpl implements FranchiseEntityMapper {

    @Override
    public FranchiseEntity toEntity(Franchise model) {
        if ( model == null ) {
            return null;
        }

        FranchiseEntity franchiseEntity = new FranchiseEntity();

        franchiseEntity.setName( model.getName() );

        return franchiseEntity;
    }

    @Override
    public Franchise toModel(FranchiseEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Franchise franchise = new Franchise();

        franchise.setId( entity.getId() );
        franchise.setName( entity.getName() );

        return franchise;
    }
}
