package com.paulanussbrand.store.entities;

import com.paulanussbrand.store.dto.InventoryRequest;
import com.paulanussbrand.store.embeddable.ProductImage;
import com.paulanussbrand.store.enums.ProductCollection;
import jakarta.persistence.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String shortDescription;

    @ElementCollection
    @CollectionTable(name = "colors", joinColumns = @JoinColumn(name = "product_id"))
    private List<String> colors;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCollection collection;

//    @ElementCollection(fetch = FetchType.EAGER)
    @ElementCollection()
    @CollectionTable(name = "images", joinColumns = @JoinColumn(name = "product_id"))
    private List<ProductImage> images;

    @ElementCollection
    @CollectionTable(name = "inventory", joinColumns = @JoinColumn(name = "product_id"))
    private List<InventoryRequest> inventory;

    @Column(columnDefinition = "TEXT")
    private java.lang.String fullDescription;

    private boolean isAvailable = true;
    private boolean isFeatured = false;

    @CurrentTimestamp
    private LocalDateTime createdAt;

    public void addProductInventory(InventoryRequest productStock){
        getInventory().add(productStock);
    }
    public void addProductImage(ProductImage productImage) { getImages().add(productImage);}
    public List<InventoryRequest> getInventory() {
        return inventory;
    }
    public void setInventory(List<InventoryRequest> inventory) {
        this.inventory = inventory;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public java.lang.String getSku() { return sku; }
    public void setSku(java.lang.String sku) { this.sku = sku; }
    public java.lang.String getName() { return name; }
    public void setName(java.lang.String name) { this.name = name; }
    public java.lang.String getShortDescription() { return shortDescription; }
    public void setShortDescription(java.lang.String shortDescription) { this.shortDescription = shortDescription; }
    public java.lang.String getFullDescription() { return fullDescription; }
    public void setFullDescription(java.lang.String fullDescription) { this.fullDescription = fullDescription; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public ProductCollection getCollection() {
        return collection;
    }
    public void setCollection(ProductCollection collection) {
        this.collection = collection;
    }
    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<ProductImage> getImages() {
        return images;
    }
    public void setImages(List<ProductImage> images) {
        this.images = images;
    }
}