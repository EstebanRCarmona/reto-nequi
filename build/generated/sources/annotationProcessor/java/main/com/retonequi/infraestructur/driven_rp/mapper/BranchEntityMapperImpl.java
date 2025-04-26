package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Branch;
import com.retonequi.infraestructur.driven_rp.entity.BranchEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T16:29:46-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class BranchEntityMapperImpl implements BranchEntityMapper {

    @Override
    public Branch toModel(BranchEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Branch branch = new Branch();

        branch.setId( entity.getId() );
        branch.setName( entity.getName() );
        branch.setFranchiseId( entity.getFranchiseId() );

        return branch;
    }

    @Override
    public BranchEntity toEntity(Branch model) {
        if ( model == null ) {
            return null;
        }

        BranchEntity branchEntity = new BranchEntity();

        branchEntity.setId( model.getId() );
        branchEntity.setName( model.getName() );
        branchEntity.setFranchiseId( model.getFranchiseId() );

        return branchEntity;
    }
}
