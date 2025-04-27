package com.retonequi.infraestructur.driving_http.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithBranchDto {
    private Branch branch;
    @JsonIgnoreProperties({"branchId"})
    private Product product;
    
}
