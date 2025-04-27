package com.retonequi.infraestructur.driving_http.mapper;

import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.responses.BranchWithFranchise;
import com.retonequi.infraestructur.driving_http.dto.response.BranchWithFranchiseDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T20:28:34-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class BranchWithFranchiseDtoMapperImpl implements BranchWithFranchiseDtoMapper {

    @Override
    public BranchWithFranchiseDto toDto(BranchWithFranchise pair) {
        if ( pair == null ) {
            return null;
        }

        BranchWithFranchiseDto branchWithFranchiseDto = new BranchWithFranchiseDto();

        branchWithFranchiseDto.setId( pairBranchId( pair ) );
        branchWithFranchiseDto.setName( pairBranchName( pair ) );
        branchWithFranchiseDto.setFranchise( pair.getFranchise() );

        return branchWithFranchiseDto;
    }

    private Long pairBranchId(BranchWithFranchise branchWithFranchise) {
        if ( branchWithFranchise == null ) {
            return null;
        }
        Branch branch = branchWithFranchise.getBranch();
        if ( branch == null ) {
            return null;
        }
        Long id = branch.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String pairBranchName(BranchWithFranchise branchWithFranchise) {
        if ( branchWithFranchise == null ) {
            return null;
        }
        Branch branch = branchWithFranchise.getBranch();
        if ( branch == null ) {
            return null;
        }
        String name = branch.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
