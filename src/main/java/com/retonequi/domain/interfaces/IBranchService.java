package com.retonequi.domain.interfaces;

import com.retonequi.domain.dto.BranchWithFranchiseDto;
import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface IBranchService {
    Mono<Branch> createBranch(String name, Long franchiseId);
    Mono<PageResponse<BranchWithFranchiseDto>> getAllBranchesWithFranchisePaged(int page, int size);
}
