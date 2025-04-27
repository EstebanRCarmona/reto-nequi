package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.interfaces.IProductService;
import com.retonequi.domain.model.Product;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.dto.response.ProductWithBranchDto;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;
import com.retonequi.infraestructur.driving_http.mapper.ProductWithBranchDtoMapper;
import com.retonequi.infraestructur.util.ConstantsInfraestructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor    
public class ProductController {
    private final IProductService productService;
    private final PageResponseDtoMapper pageResponseDtoMapper;
    private final ProductWithBranchDtoMapper productWithBranchDtoMapper;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProductToBranch(@RequestParam String name,
                                            @RequestParam Integer stock,
                                            @RequestParam Long branchId) {
        return productService.addProductToBranch(name, stock, branchId);
    }

    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeProductFromBranch(@PathVariable Long productId) {
        return productService.removeProductFromBranch(productId);
    }

    @PutMapping("/update/{productId}")
    public Mono<Product> updateProductStock(@PathVariable Long productId,
                                            @RequestParam Integer stock) {
        return productService.updateProductStock(productId, stock);
    }

    @GetMapping("/{branchId}")
    public Mono<PageResponseDto<Product>> getProductsByBranch(
            @PathVariable Long branchId,
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return productService.getProductsByBranch(branchId, page, size)
            .map(pageResponseDtoMapper::toDto);
    }

    @GetMapping("/max-stock/by-branch/franchise/{franchiseId}")
    public Mono<PageResponseDto<ProductWithBranchDto>> getProductsMaxStockByBranchForFranchise(
            @PathVariable Long franchiseId,
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return productService.getProductsMaxStockByBranchForFranchise(franchiseId, page, size)
            .map(pageResponse -> pageResponseDtoMapper.toDto(pageResponse, productWithBranchDtoMapper::toDto));
    }

}