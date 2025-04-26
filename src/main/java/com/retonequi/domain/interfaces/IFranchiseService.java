package com.retonequi.domain.interfaces;

import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseService {
    Mono<Franchise> createFranchise(String name);
    Mono<Franchise> updateFranchiseName(Long id, String name);
    Mono<PageResponse<Franchise>> getAllFranchisesPaged(int page, int size);
}
