package com.retonequi.domain.services;

import com.retonequi.domain.dto.BranchWithFranchiseDto;
import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IBranchService;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IPaginator;
import com.retonequi.domain.model.Branch;
import com.retonequi.domain.util.ConstantsDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService implements IBranchService {
    private final IBranchPersistence branchPersistence;
    private final IFranchisePersistence franchisePersistence;
    private final IPaginator<Branch> paginator;

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
    public Mono<PageResponse<BranchWithFranchiseDto>> getAllBranchesWithFranchisePaged(int page, int size) {
        return Mono.zip(
            paginator.count(),
            paginator.findPage(page, size)
                .flatMap(branch -> franchisePersistence.findById(branch.getFranchiseId())
                        .map(franchise -> new BranchWithFranchiseDto(branch.getId(), branch.getName(), franchise)))
                .collectList()
        ).map(tuple -> {
            long totalElements = tuple.getT1();
            java.util.List<BranchWithFranchiseDto> content = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            return new PageResponse<>(content, page, size, totalElements, totalPages);
        });
    }
}
