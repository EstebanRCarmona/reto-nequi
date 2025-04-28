package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.interfaces.IFranchiseService;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;
import com.retonequi.infraestructur.util.ConstantsInfraestructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Franchise Controller", description = "Endpoints for managing franchises")
@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final IFranchiseService franchiseService;
    private final PageResponseDtoMapper pageResponseDtoMapper;

    @Operation(summary = "Create a new franchise", description = "Creates a new franchise with the specified name.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Franchise created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(
            @Parameter(description = "Name of the franchise", required = true) @Valid @RequestParam String name) {
        return franchiseService.createFranchise(name);
    }

    @Operation(summary = "Update franchise name", description = "Updates the name of an existing franchise by ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franchise updated successfully"),
        @ApiResponse(responseCode = "404", description = "Franchise not found")
    })
    @PutMapping("/{id}")
    public Mono<Franchise> updateFranchiseName(
            @Parameter(description = "ID of the franchise to update", required = true) @PathVariable Long id,
            @Parameter(description = "New name for the franchise", required = true) @Valid @RequestParam String name) {
        return franchiseService.updateFranchiseName(id, name);
    }

    @Operation(summary = "Get all franchises (paginated)", description = "Retrieves a paginated list of all franchises.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Franchises retrieved successfully")
    })
    @GetMapping
    public Mono<PageResponseDto<Franchise>> getAllFranchisesPaged(
            @Parameter(description = "Page number", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @Parameter(description = "Page size", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return franchiseService.getAllFranchisesPaged(page, size)
            .map(pageResponseDtoMapper::toDto);
    }
}
