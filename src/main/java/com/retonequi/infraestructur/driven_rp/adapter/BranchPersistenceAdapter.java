package com.retonequi.infraestructur.driven_rp.adapter;

import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IPaginator;
import com.retonequi.domain.model.Branch;
import com.retonequi.infraestructur.driven_rp.entity.BranchEntity;
import com.retonequi.infraestructur.driven_rp.mapper.BranchEntityMapper;
import com.retonequi.infraestructur.driven_rp.persistence.IBranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchPersistenceAdapter implements IBranchPersistence, IPaginator<Branch> {
    private final IBranchRepository branchRepository;
    private final BranchEntityMapper branchEntityMapper;

    @Override
    public Mono<Branch> save(Branch branch) {
        BranchEntity entity = branchEntityMapper.toEntity(branch);
        entity.setId(null);
        return branchRepository.save(entity)
                .map(branchEntityMapper::toModel);
    }

    @Override
    public Flux<Branch> findAll() {
        return branchRepository.findAll().map(branchEntityMapper::toModel);
    }

    @Override
    public Mono<Long> count() {
        return branchRepository.count();
    }

    @Override
    public Flux<Branch> findPage(int page, int size) {
        return branchRepository.findAll()
                .skip((long) (page - 1) * size)
                .take(size)
                .map(branchEntityMapper::toModel);
    }

    @Override
    public Mono<Branch> findByNameIgnoreCase(String name) {
        return branchRepository.findAll()
                .filter(b -> b.getName() != null && b.getName().equalsIgnoreCase(name))
                .next()
                .map(branchEntityMapper::toModel);
    }
}
