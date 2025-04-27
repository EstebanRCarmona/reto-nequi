package com.retonequi.domain.interfaces;

import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.PageResponse;
import com.retonequi.domain.model.responses.BranchWithFranchise;

import reactor.core.publisher.Mono;

public interface IBranchService {
    Mono<Branch> createBranch(String name, Long franchiseId);
    Mono<PageResponse<BranchWithFranchise>> getAllBranchesWithFranchisePaged(int page, int size);
}
