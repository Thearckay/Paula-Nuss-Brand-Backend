package com.paulanussbrand.store.repository;

import com.paulanussbrand.store.entities.Product;
import com.paulanussbrand.store.enums.ProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsBySku(String sku);
    Optional<Product> findProjectById(UUID id);
    List<Product> findByNameContainingIgnoreCaseOrSkuIgnoreCase(String nameToSearch, String skuToSearch);
    Optional<Product> findBySkuIgnoreCase(String sku);
    List<Product> findByCollection(ProductCollection collection);
    Page<Product> findByCollection(ProductCollection productCollection, Pageable pageable);
    List<Product> findByIsFeaturedTrue();
}
