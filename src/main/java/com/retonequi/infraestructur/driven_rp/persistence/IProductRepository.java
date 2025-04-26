package com.retonequi.infraestructur.driven_rp.persistence;

import com.retonequi.infraestructur.driven_rp.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

}