package com.retonequi.infraestructur.driven_rp.persistence;

import com.retonequi.infraestructur.driven_rp.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {
}
