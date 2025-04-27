package com.retonequi.domain.services;

import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IBranchService;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.PageResponse;
import com.retonequi.domain.model.responses.BranchWithFranchise;
import com.retonequi.domain.util.ConstantsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {
    private final IBranchPersistence branchPersistence;
    private final IFranchisePersistence franchisePersistence;

    @Override
    public Mono<Branch> createBranch(String name, Long franchiseId) {
        if (name == null || name.isEmpty() || franchiseId == null) {
            throw new ErrorBadRequest(ConstantsDomain.ERR_NAME_AND_FRANCHISE_ID_NULL);
        }
        return branchPersistence.findByNameIgnoreCase(name)
            .flatMap(existing -> Mono.<Branch>error(new ExceptionAlreadyExist(ConstantsDomain.ERR_BRANCH_ALREADY_EXISTS)))
            .switchIfEmpty(
                franchisePersistence.findById(franchiseId)
                    .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_FRANCHISE_NOT_FOUND + franchiseId)))
                    .flatMap(franchise -> branchPersistence.save(new Branch(null, name, franchiseId)))
            );
    }

    @Override
    public Mono<PageResponse<BranchWithFranchise>> getAllBranchesWithFranchisePaged(int page, int size) {
        return branchPersistence.findAll()
            .flatMap(branch -> franchisePersistence.findById(branch.getFranchiseId())
                .map(franchise -> new BranchWithFranchise(branch, franchise))
            )
            .collectList()
            .map(list -> {
                int totalElements = list.size();
                int totalPages = (int) Math.ceil((double) totalElements / size);
                int fromIndex = Math.max(0, Math.min((page - 1) * size, totalElements));
                int toIndex = Math.max(0, Math.min(fromIndex + size, totalElements));
                java.util.List<BranchWithFranchise> pagedList = list.subList(fromIndex, toIndex);
                return new PageResponse<>(pagedList, page, size, totalElements, totalPages);
            });
    }
}
