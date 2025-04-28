package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.interfaces.IBranchService;
import com.retonequi.domain.model.Branch;
import com.retonequi.infraestructur.driving_http.dto.response.BranchWithFranchiseDto;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.mapper.BranchWithFranchiseDtoMapper;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static com.retonequi.infraestructur.util.ConstantsInfraestructure.DEFAULT_PAGE;
import static com.retonequi.infraestructur.util.ConstantsInfraestructure.DEFAULT_SIZE;

@Tag(name = "Branch Controller", description = "Endpoints for managing branches")
@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final IBranchService branchService;
    private final PageResponseDtoMapper pageResponseDtoMapper;
    private final BranchWithFranchiseDtoMapper branchWithFranchiseDtoMapper;

    @Operation(summary = "Create a new branch", description = "Creates a new branch associated with a given franchise.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Branch created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> createBranch(
            @Parameter(description = "Name of the branch", required = true) @RequestParam String name,
            @Parameter(description = "ID of the franchise", required = true) @RequestParam Long franchiseId) {
        return branchService.createBranch(name, franchiseId);
    }

    @Operation(summary = "Get all branches with their franchises", description = "Returns a paged list of all branches with their associated franchises.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Branches retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping("/with-franchise")
    public Mono<PageResponseDto<BranchWithFranchiseDto>> getAllBranchesWithFranchise(
            @Parameter(description = "Page number", required = true) @RequestParam(defaultValue = DEFAULT_PAGE) int page,
            @Parameter(description = "Page size", required = true) @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        return branchService.getAllBranchesWithFranchisePaged(page, size)
            .map(pageResponse -> pageResponseDtoMapper.toDto(pageResponse, branchWithFranchiseDtoMapper::toDto));
    }

}