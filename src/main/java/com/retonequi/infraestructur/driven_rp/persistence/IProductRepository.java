package com.retonequi.infraestructur.driven_rp.persistence;

import com.retonequi.infraestructur.driven_rp.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {
    Flux<ProductEntity> findAllByBranchId(Long branchId);
}