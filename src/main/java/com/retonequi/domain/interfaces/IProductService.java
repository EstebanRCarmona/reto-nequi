package com.retonequi.domain.interfaces;

import com.retonequi.domain.model.PageResponse;
import com.retonequi.domain.model.Product;
import com.retonequi.domain.model.responses.ProductBranchPair;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> addProductToBranch(String name, Integer stock, Long branchId);
    Mono<Void> removeProductFromBranch(Long productId);
    Mono<Product> updateProductStock(Long productId, Integer stock);
    Mono<PageResponse<ProductBranchPair>> getProductsMaxStockByBranchForFranchise(Long franchiseId, int page, int size);
    Mono<PageResponse<Product>> getProductsByBranch(Long branchId, int page, int size);
}