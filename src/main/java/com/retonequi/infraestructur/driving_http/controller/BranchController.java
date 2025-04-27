package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.interfaces.IBranchService;
import com.retonequi.domain.model.Branch;
import com.retonequi.infraestructur.driving_http.dto.response.BranchWithFranchiseDto;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.mapper.BranchWithFranchiseDtoMapper;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;

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
    private final PageResponseDtoMapper pageResponseDtoMapper;
    private final BranchWithFranchiseDtoMapper branchWithFranchiseDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> createBranch(@RequestParam String name, @RequestParam Long franchiseId) {
        return branchService.createBranch(name, franchiseId);
    }

    @GetMapping("/with-franchise")
    public Mono<PageResponseDto<BranchWithFranchiseDto>> getAllBranchesWithFranchise(
            @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        return branchService.getAllBranchesWithFranchisePaged(page, size)
            .map(pageResponse -> pageResponseDtoMapper.toDto(pageResponse, branchWithFranchiseDtoMapper::toDto));
    }

}