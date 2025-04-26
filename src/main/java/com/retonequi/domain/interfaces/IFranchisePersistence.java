package com.retonequi.domain.interfaces;

import com.retonequi.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistence {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> updateName(Long id, String name);
    Mono<Franchise> findByName(String name);
    Mono<Franchise> findById(Long id);
}
