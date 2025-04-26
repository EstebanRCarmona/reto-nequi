package com.retonequi.domain.services;

import com.retonequi.domain.interfaces.IFranchiseService;
import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IPaginator;
import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.model.Franchise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService implements IFranchiseService {
    private final IFranchisePersistence franchisePersistence;
    private final IPaginator<Franchise> paginator;

    @Override
    public Mono<Franchise> createFranchise(String name) {
        if (name == null || name.isEmpty()) {
            throw new ErrorBadRequest("Name cannot be null");
        }
        return franchisePersistence.findByName(name)
            .flatMap(existing -> Mono.<Franchise>error(new ExceptionAlreadyExist("Franchise already exists")))
            .switchIfEmpty(franchisePersistence.save(new Franchise(null, name)));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        if (name == null || name.isEmpty()) {
            throw new ErrorBadRequest("Name cannot be null");
        }

        return franchisePersistence.findById(id)
            .switchIfEmpty(Mono.error(new ErrorNotFound("Franchise not found: "+id)))
            .flatMap(existing -> franchisePersistence.updateName(id, name));
    }

    @Override
    public Mono<PageResponse<Franchise>> getAllFranchisesPaged(int page, int size) {
        return Mono.zip(
            paginator.count(),
            paginator.findPage(page, size).collectList()
        ).map(tuple -> {
            long totalElements = tuple.getT1();
            java.util.List<Franchise> content = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            return new PageResponse<>(content, page, size, totalElements, totalPages);
        });
    }
}
