package com.retonequi.domain.interfaces;

import com.retonequi.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchPersistence {
    Mono<Branch> save(Branch branch);
    Flux<Branch> findAll();
    Mono<Branch> findByNameIgnoreCase(String name);
    Mono<Branch> findById(Long id);
}
