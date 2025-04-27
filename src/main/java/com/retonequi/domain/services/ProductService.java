package com.retonequi.domain.services;

import com.retonequi.domain.model.PageResponse;
import com.retonequi.domain.model.Product;
import com.retonequi.domain.model.responses.ProductBranchPair;
import com.retonequi.domain.exception.ErrorBadRequest;
import com.retonequi.domain.exception.ErrorNotFound;
import com.retonequi.domain.exception.ExceptionAlreadyExist;
import com.retonequi.domain.interfaces.IBranchPersistence;
import com.retonequi.domain.interfaces.IFranchisePersistence;
import com.retonequi.domain.interfaces.IProductPersistence;
import com.retonequi.domain.interfaces.IProductService;
import com.retonequi.domain.util.ConstantsDomain;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductPersistence productPersistence;
    private final IBranchPersistence branchPersistence;
    private final IFranchisePersistence franchisePersistence;

    @Override
    public Mono<Product> addProductToBranch(String name, Integer stock, Long branchId) {
        if (name == null || name.isEmpty()) {
            throw new ErrorBadRequest(ConstantsDomain.ERR_PRODUCT_NAME_NULL);
        }
        if (stock == null || stock <= 0) {
            throw new ErrorBadRequest(ConstantsDomain.ERR_PRODUCT_STOCK_NEGATIVE);
        }
        return branchPersistence.findById(branchId)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_BRANCH_NOT_FOUND + branchId)))
            .flatMap(branch -> productPersistence.findAllProductsByBranch(branchId)
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .hasElements()
                .flatMap(exists -> exists
                    ? Mono.<Product>error(new ExceptionAlreadyExist(ConstantsDomain.ERR_PRODUCT_ALREADY_EXISTS))
                    : productPersistence.saveProduct(new Product(null, name, stock, branchId))
                )
            );
    }

    @Override
    public Mono<Void> removeProductFromBranch(Long productId) {
        return productPersistence.findProductById(productId)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_PRODUCT_NOT_FOUND + productId)))
            .flatMap(product -> productPersistence.deleteProduct(productId));
    }

    @Override
    public Mono<Product> updateProductStock(Long productId, Integer stock) {
        if (stock == null || stock <= 0) {
            throw new ErrorBadRequest(ConstantsDomain.ERR_PRODUCT_STOCK_NEGATIVE);
        }
        return productPersistence.findProductById(productId)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_PRODUCT_NOT_FOUND + productId)))
            .flatMap(product -> {
                product.setStock(stock);
                return productPersistence.saveProduct(product);
            });
    }

    @Override
    public Mono<PageResponse<ProductBranchPair>> getProductsMaxStockByBranchForFranchise(Long franchiseId, int page, int size) {
        if (franchiseId == null) {
            throw new ErrorBadRequest(ConstantsDomain.ERR_FRANCHISE_ID_NULL);
        }
        return franchisePersistence.findById(franchiseId)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_FRANCHISE_NOT_FOUND + franchiseId)))
            .then(
                branchPersistence.findAll()
                    .filter(branch -> branch.getFranchiseId().equals(franchiseId))
                    .flatMap(branch -> productPersistence.findAllProductsByBranch(branch.getId())
                        .collectList()
                        .filter(list -> !list.isEmpty())
                        .map(list -> {
                            Product maxStockProduct = list.stream().max(java.util.Comparator.comparing(Product::getStock)).orElse(null);
                            if (maxStockProduct != null) {
                                Product productWithoutBranchId = new Product(maxStockProduct.getId(), maxStockProduct.getName(), maxStockProduct.getStock(), null);
                                return new ProductBranchPair(productWithoutBranchId, branch);
                            }
                            return null;
                        })
                        .filter(pair -> pair != null)
                    )
                    .collectList()
                    .map(list -> {
                        int totalElements = list.size();
                        int totalPages = (int) Math.ceil((double) totalElements / size);
                        int fromIndex = Math.max(0, Math.min((page - 1) * size, totalElements));
                        int toIndex = Math.max(0, Math.min(fromIndex + size, totalElements));
                        java.util.List<ProductBranchPair> pagedList = list.subList(fromIndex, toIndex);
                        return new PageResponse<>(pagedList, page, size, totalElements, totalPages);
                    })
            );
    }

    @Override
    public Mono<PageResponse<Product>> getProductsByBranch(Long branchId, int page, int size) {
        return branchPersistence.findById(branchId)
            .switchIfEmpty(Mono.error(new ErrorNotFound(ConstantsDomain.ERR_BRANCH_NOT_FOUND + branchId)))
            .then(productPersistence.findAllProductsByBranch(branchId)
                .collectList()
                .flatMap(list -> {
                    int totalElements = list.size();
                    int totalPages = (int) Math.ceil((double) totalElements / size);
                    int fromIndex = Math.max(0, Math.min((page - 1) * size, totalElements));
                    int toIndex = Math.max(0, Math.min(fromIndex + size, totalElements));
                    java.util.List<Product> pagedList = list.subList(fromIndex, toIndex);
                    PageResponse<Product> response = new PageResponse<>(pagedList, page, size, totalElements, totalPages);
                    return Mono.just(response);
                })
            );
    }
}
