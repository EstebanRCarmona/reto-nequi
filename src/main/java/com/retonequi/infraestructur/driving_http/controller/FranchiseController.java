package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.interfaces.IFranchiseService;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;
import com.retonequi.infraestructur.util.ConstantsInfraestructure;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franchises")
@RequiredArgsConstructor
public class FranchiseController {
    private final IFranchiseService franchiseService;
    private final PageResponseDtoMapper pageResponseDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@Valid @RequestParam String name) {
        return franchiseService.createFranchise(name);
    }

    @PutMapping("/{id}")
    public Mono<Franchise> updateFranchiseName(@PathVariable Long id, @Valid @RequestParam String name) {
        return franchiseService.updateFranchiseName(id, name);
    }

    @GetMapping
    public Mono<PageResponseDto<Franchise>> getAllFranchisesPaged(
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return franchiseService.getAllFranchisesPaged(page, size)
            .map(pageResponseDtoMapper::toDto);
    }
}
