package com.retonequi.domain.services;

import com.retonequi.domain.interfaces.IFranchiseService;
import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IPaginator;
import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.model.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import static com.retonequi.domain.util.ConstantsDomain.*;

@Service
@RequiredArgsConstructor
public class FranchiseService implements IFranchiseService {
    private final IFranchisePersistence franchisePersistence;
    private final IPaginator<Franchise> paginator;

    @Override
    public Mono<Franchise> createFranchise(String name) {
        if (name == null || name.isEmpty()) {
            throw new ErrorBadRequest(ERR_NAME_NULL);
        }
        return franchisePersistence.findByName(name)
            .flatMap(existing -> Mono.<Franchise>error(new ExceptionAlreadyExist(ERR_FRANCHISE_ALREADY_EXISTS)))
            .switchIfEmpty(franchisePersistence.save(new Franchise(null, name)));
    }

    @Override
    public Mono<Franchise> updateFranchiseName(Long id, String name) {
        if (name == null || name.isEmpty()) {
            throw new ErrorBadRequest(ERR_NAME_NULL);
        }

        return franchisePersistence.findById(id)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ERR_FRANCHISE_NOT_FOUND_ID + id)))
            .flatMap(existing -> franchisePersistence.updateName(id, name));
    }

    @Override
    public Mono<PageResponse<Franchise>> getAllFranchisesPaged(int page, int size) {
        return paginator.count()
            .flatMap(total -> paginator.findPage(page, size).collectList()
                .map(content -> new PageResponse<>(content, page, size, total, (int) Math.ceil((double) total / size))));
    }
}
