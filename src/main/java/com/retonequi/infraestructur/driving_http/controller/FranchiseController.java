package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.model.Franchise;
import com.retonequi.domain.dto.PageResponse;
import com.retonequi.domain.interfaces.IFranchiseService;
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
    public Mono<PageResponse<Franchise>> getAllFranchises(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return franchiseService.getAllFranchisesPaged(page, size);
    }
}
