package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.dto.BranchWithFranchiseDto;
import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.interfaces.IBranchService;
import com.retonequi.domain.model.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.retonequi.infraestructur.util.ConstantsInfraestructure.DEFAULT_PAGE;
import static com.retonequi.infraestructur.util.ConstantsInfraestructure.DEFAULT_SIZE;

@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final IBranchService branchService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> createBranch(@RequestParam String name, @RequestParam Long franchiseId) {
        return branchService.createBranch(name, franchiseId);
    }


    @GetMapping
    public Mono<PageResponse<BranchWithFranchiseDto>> getAllBranchesWithFranchise(
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        return branchService.getAllBranchesWithFranchisePaged(page, size);
    }
}
