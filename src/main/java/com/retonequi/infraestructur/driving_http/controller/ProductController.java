package com.retonequi.infraestructur.driving_http.controller;

import com.retonequi.domain.interfaces.IProductService;
import com.retonequi.domain.model.Product;
import com.retonequi.infraestructur.driving_http.dto.response.PageResponseDto;
import com.retonequi.infraestructur.driving_http.dto.response.ProductWithBranchDto;
import com.retonequi.infraestructur.driving_http.mapper.PageResponseDtoMapper;
import com.retonequi.infraestructur.driving_http.mapper.ProductWithBranchDtoMapper;
import com.retonequi.infraestructur.util.ConstantsInfraestructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Product Controller", description = "Endpoints for managing products")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor    
public class ProductController {
    private final IProductService productService;
    private final PageResponseDtoMapper pageResponseDtoMapper;
    private final ProductWithBranchDtoMapper productWithBranchDtoMapper;

    @Operation(summary = "Add a product to a branch", description = "Creates and adds a new product to a specific branch.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProductToBranch(
            @Parameter(description = "Name of the product", required = true) @RequestParam String name,
            @Parameter(description = "Stock quantity", required = true) @RequestParam Integer stock,
            @Parameter(description = "ID of the branch", required = true) @RequestParam Long branchId) {
        return productService.addProductToBranch(name, stock, branchId);
    }

    @Operation(summary = "Remove a product from a branch", description = "Deletes a product from a branch by product ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product removed successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> removeProductFromBranch(
            @Parameter(description = "ID of the product to remove", required = true) @PathVariable Long productId) {
        return productService.removeProductFromBranch(productId);
    }

    @Operation(summary = "Update product stock", description = "Updates the stock of a product by product ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product stock updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/update/{productId}")
    public Mono<Product> updateProductStock(
            @Parameter(description = "ID of the product to update", required = true) @PathVariable Long productId,
            @Parameter(description = "New stock quantity", required = true) @RequestParam Integer stock) {
        return productService.updateProductStock(productId, stock);
    }

    @Operation(summary = "Get products by branch", description = "Retrieves a paginated list of products for a specific branch.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping("/{branchId}")
    public Mono<PageResponseDto<Product>> getProductsByBranch(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId,
            @Parameter(description = "Page number", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @Parameter(description = "Page size", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return productService.getProductsByBranch(branchId, page, size)
            .map(pageResponseDtoMapper::toDto);
    }

    @Operation(summary = "Get products with max stock by branch for a franchise", description = "Retrieves a paginated list of products with the maximum stock per branch for a specific franchise.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping("/max-stock/by-branch/franchise/{franchiseId}")
    public Mono<PageResponseDto<ProductWithBranchDto>> getProductsMaxStockByBranchForFranchise(
            @Parameter(description = "ID of the franchise", required = true) @PathVariable Long franchiseId,
            @Parameter(description = "Page number", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_PAGE) int page,
            @Parameter(description = "Page size", required = false) @RequestParam(defaultValue = ConstantsInfraestructure.DEFAULT_SIZE) int size) {
        return productService.getProductsMaxStockByBranchForFranchise(franchiseId, page, size)
            .map(pageResponse -> pageResponseDtoMapper.toDto(pageResponse, productWithBranchDtoMapper::toDto));
    }
}