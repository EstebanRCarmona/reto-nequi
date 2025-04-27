package com.retonequi.infraestructur.driven_rp.mapper;

import com.retonequi.domain.model.Product;
import com.retonequi.infraestructur.driven_rp.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T18:06:30-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.8.jar, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class ProductEntityMapperImpl implements ProductEntityMapper {

    @Override
    public Product toModel(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( entity.getId() );
        product.setName( entity.getName() );
        product.setStock( entity.getStock() );
        product.setBranchId( entity.getBranchId() );

        return product;
    }

    @Override
    public ProductEntity toEntity(Product model) {
        if ( model == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( model.getId() );
        productEntity.setName( model.getName() );
        productEntity.setStock( model.getStock() );
        productEntity.setBranchId( model.getBranchId() );

        return productEntity;
    }
}
