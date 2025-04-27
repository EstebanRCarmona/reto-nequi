package com.retonequi.domain.dto;

import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Franchise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchWithFranchiseDto {
    private Long id;
    private String name;
    private Franchise franchise;
}
