package com.retonequi.infraestructur.driven_rp.persistence;

import com.retonequi.infraestructur.driven_rp.entity.FranchiseEntity;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
    @Query("SELECT * FROM franchises WHERE LOWER(name) = LOWER(:name)")
Mono<FranchiseEntity> findByNameIgnoreCase(String name);
}
