package com.retonequi.domain.services;

import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IProductPersistence;
import com.retonequi.domain.model.Branch;
import com.retonequi.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private IProductPersistence productPersistence;
    private IBranchPersistence branchPersistence;
    private IFranchisePersistence franchisePersistence;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productPersistence = Mockito.mock(IProductPersistence.class);
        branchPersistence = Mockito.mock(IBranchPersistence.class);
        franchisePersistence = Mockito.mock(IFranchisePersistence.class);
        productService = new ProductService(productPersistence, branchPersistence, franchisePersistence);
    }

    @Test
    void testAddProductToBranch_Success() {
        when(branchPersistence.findById(2L)).thenReturn(Mono.just(new Branch(2L, "Sucursal", 1L)));
        when(productPersistence.findAllProductsByBranch(2L)).thenReturn(Flux.empty());
        when(productPersistence.saveProduct(any(Product.class))).thenReturn(Mono.just(new Product(1L, "P2", 5, 2L)));
        Mono<Product> result = productService.addProductToBranch("P2", 5, 2L);
        Product product = result.block();
        assertNotNull(product);
        assertEquals("P2", product.getName());
        assertEquals(5, product.getStock());
    }

    @Test
    void testAddProductToBranch_BranchNotFound() {
        when(branchPersistence.findById(99L)).thenReturn(Mono.empty());
        Mono<Product> result = productService.addProductToBranch("P2", 5, 99L);
        Exception ex = assertThrows(ErrorNotFound.class, result::block);
        assertTrue(ex.getMessage().toLowerCase().contains("branch"));
    }

    @Test
    void testAddProductToBranch_ProductAlreadyExists() {
        when(branchPersistence.findById(2L)).thenReturn(Mono.just(new Branch(2L, "Sucursal", 1L)));
        when(productPersistence.findAllProductsByBranch(2L))
            .thenReturn(Flux.just(new Product(2L, "P2", 10, 2L)));
        when(productPersistence.saveProduct(any(Product.class))).thenThrow(new RuntimeException("Should not be called"));
        Mono<Product> result = productService.addProductToBranch("P2", 5, 2L);
        Exception ex = assertThrows(ExceptionAlreadyExist.class, result::block);
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void testRemoveProductFromBranch_Success() {
        when(productPersistence.findProductById(1L)).thenReturn(Mono.just(new Product(1L, "P1", 5, 2L)));
        when(productPersistence.deleteProduct(1L)).thenReturn(Mono.empty());
        Mono<Void> result = productService.removeProductFromBranch(1L);
        assertDoesNotThrow(() -> result.block());
    }

    @Test
    void testRemoveProductFromBranch_NotFound() {
        when(productPersistence.findProductById(99L)).thenReturn(Mono.empty());
        Mono<Void> result = productService.removeProductFromBranch(99L);
        Exception ex = assertThrows(ErrorNotFound.class, result::block);
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testUpdateProductStock_Success() {
        when(productPersistence.findProductById(1L)).thenReturn(Mono.just(new Product(1L, "P1", 5, 2L)));
        when(productPersistence.saveProduct(any(Product.class))).thenReturn(Mono.just(new Product(1L, "P1", 20, 2L)));
        Mono<Product> result = productService.updateProductStock(1L, 20);
        Product product = result.block();
        assertNotNull(product);
        assertEquals(20, product.getStock());
    }

    @Test
    void testUpdateProductStock_NotFound() {
        when(productPersistence.findProductById(99L)).thenReturn(Mono.empty());
        Mono<Product> result = productService.updateProductStock(99L, 10);
        Exception ex = assertThrows(ErrorNotFound.class, result::block);
        assertTrue(ex.getMessage().contains("not found"));
    }


    @Test
    void testGetProductsMaxStockByBranchForFranchise_Success_Empty() {
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.just(Mockito.mock(com.retonequi.domain.model.Franchise.class)));
        when(branchPersistence.findAll()).thenReturn(Flux.empty());
        Mono<?> result = productService.getProductsMaxStockByBranchForFranchise(1L, 0, 10);
        Object page = result.block();
        assertNotNull(page);
     }

    @Test
    void testGetProductsMaxStockByBranchForFranchise_Success_WithProducts() {
        when(franchisePersistence.findById(anyLong())).thenReturn(Mono.just(Mockito.mock(com.retonequi.domain.model.Franchise.class)));
        when(branchPersistence.findAll()).thenReturn(Flux.just(
            new Branch(1L, "Sucursal1", 1L),
            new Branch(2L, "Sucursal2", 1L)
        ));
        when(productPersistence.findAllProductsByBranch(1L)).thenReturn(Flux.just(
            new Product(1L, "P1", 10, 1L),
            new Product(2L, "P2", 15, 1L)
        ));
        when(productPersistence.findAllProductsByBranch(2L)).thenReturn(Flux.just(
            new Product(3L, "P3", 20, 2L)
        ));
        Mono<?> result = productService.getProductsMaxStockByBranchForFranchise(1L, 0, 10);
        Object page = result.block();
        assertNotNull(page);
    }
}
