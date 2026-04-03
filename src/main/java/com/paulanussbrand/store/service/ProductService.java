package com.paulanussbrand.store.service;

import com.cloudinary.utils.ObjectUtils;
import com.paulanussbrand.store.config.CloudinaryConfig;
import com.paulanussbrand.store.dto.*;
import com.paulanussbrand.store.embeddable.ProductImage;
import com.paulanussbrand.store.entities.Product;
import com.paulanussbrand.store.enums.ProductCollection;
import com.paulanussbrand.store.exceptions.ProductException;
import com.paulanussbrand.store.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CloudinaryConfig cloudinary;

    private final String URL_IMAGE_DEFAULT = "https://res.cloudinary.com/dvz6c7kzx/image/upload/v1774370312/paulaDefault_yhfb15.jpg";

    public Product createProduct(ProductRequest productRequest, List<MultipartFile> images){

        Product newProduct = new Product();
        newProduct.setFeatured(productRequest.featured());
        newProduct.setName(productRequest.name());
        newProduct.setSku(productRequest.sku());
        newProduct.setPrice(productRequest.price());
        newProduct.setShortDescription(productRequest.shortDescription());
        newProduct.setCollection(productRequest.collection());
        newProduct.setFullDescription(productRequest.fullDescription());
        newProduct.setColors(productRequest.colors());
        newProduct.setInventory(productRequest.inventory());

        if (images != null && !images.isEmpty()) newProduct.setImages(uploadFilesAndReturnThemUrlsPublicId(images));
        return productRepository.save(newProduct);
    }

    public List<ProductCatalogAdmResponse> getCatalogForAdm() {
        List<Product> productList = productRepository.findAll();

        if (productList.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductCatalogAdmResponse> catalogAdmResponseList = new ArrayList<>();

        for(Product product : productList){
            String firstImageUrl = (product.getImages() == null || product.getImages().isEmpty())
                    ? URL_IMAGE_DEFAULT
                    : product.getImages().get(0).url();

            catalogAdmResponseList.add(new ProductCatalogAdmResponse(
                    product.getId(),
                    product.getName(),
                    product.getShortDescription(),
                    firstImageUrl,
                    product.getPrice()
            ));
        }

        return catalogAdmResponseList;
    }

    public Product getProductWithId(UUID id) {
        Optional<Product> productOptional = productRepository.findProjectById(id);
        if (productOptional.isEmpty()) throw new ProductException("Id inválido!", HttpStatus.NO_CONTENT);
        return productOptional.get();
    }

    public List<ProductImage> uploadFilesAndReturnThemUrlsPublicId(List<MultipartFile> images){
        if (images == null || images.isEmpty()) return null;
        List<ProductImage> themUrlAndPublicId = new ArrayList<>();

        for (MultipartFile image : images){
            try {
                if (image != null && !image.isEmpty()){
                    Map result = cloudinary.cloudinary().uploader().upload(image.getBytes(), ObjectUtils.emptyMap());

                    String url = result.get("secure_url").toString();
                    String publicId = result.get("public_id").toString();

                    ProductImage productImage = new ProductImage(url, publicId);
                    themUrlAndPublicId.add(productImage);

                }
            } catch (IOException e){
                throw new ProductException("Erro ao tentar fazer upload do produto", HttpStatus.BAD_REQUEST);
            }

        }
        return themUrlAndPublicId;
    }
    public void checkSku(String sku){
        if (productRepository.existsBySku(sku)) throw new ProductException("Sku em uso!", HttpStatus.CONFLICT);
    }

    @Transactional
    public Product updateProduct(UUID productId, ProductRequest productUpdated, List<MultipartFile> listImage, List<String> imagePublicIdToDelete) {
        Optional<Product> optionalProduct = productRepository.findProjectById(productId);
        if (optionalProduct.isEmpty()) throw new ProductException("Id inválido", HttpStatus.NOT_FOUND);

        Product productToUpdate = optionalProduct.get();
        productToUpdate.setFeatured(productUpdated.featured());
        productToUpdate.setName(productUpdated.name());
        productToUpdate.setPrice(productUpdated.price());
        productToUpdate.setCollection(productUpdated.collection());
        productToUpdate.setShortDescription(productUpdated.shortDescription());
        productToUpdate.setColors(productUpdated.colors());
        productToUpdate.setInventory(productUpdated.inventory());
        productToUpdate.setFullDescription(productUpdated.fullDescription());

        if (imagePublicIdToDelete!=null && !imagePublicIdToDelete.isEmpty()){
            try {
                for (String imageId : imagePublicIdToDelete){
                    Map result = cloudinary.cloudinary().uploader()
                            .destroy(imageId, ObjectUtils.emptyMap());

                    productToUpdate.getImages().removeIf(productImage -> productImage.publicId().equals(imageId));
                }

            } catch (IOException e) {
                throw new ProductException("Erro ao tentar deletar imagen(s", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        if (listImage != null && !listImage.isEmpty()) productToUpdate.getImages().addAll(uploadFilesAndReturnThemUrlsPublicId(listImage));

        return productRepository.save(productToUpdate);
    }

    public void deleteProductByid(UUID productId) {
        Optional<Product> productOptional = productRepository.findProjectById(productId);
        if (productOptional.isEmpty()) throw new ProductException("Id inválido!", HttpStatus.NO_CONTENT);
        Product productToDelete = productOptional.get();
        productRepository.delete(productToDelete);
    }

    public List<SearchResultResponse> searchProduct(String stringToSearch){
        if (stringToSearch == null || stringToSearch.equals("")) return Collections.emptyList();
        List<Product> productList = productRepository.findByNameContainingIgnoreCaseOrSkuIgnoreCase(stringToSearch, stringToSearch);

        List<SearchResultResponse> resultResponseList = new ArrayList<>();
        for (Product prod : productList){
            String imageUrl = "";
            if (prod.getImages().getFirst().url() == null || prod.getImages().getFirst().url().equals("") ){
                imageUrl = URL_IMAGE_DEFAULT;
            } else {
                imageUrl = prod.getImages().getFirst().url();
            }
            SearchResultResponse response = new SearchResultResponse(
                    prod.getId(),
                    prod.getName(),
                    prod.getShortDescription(),
                    imageUrl,
                    prod.getPrice()
            );
            resultResponseList.add(response);
        }

        return resultResponseList;
    }

    public List<SearchPublicResultResponse> searchPublicProduct(String stringToSearch){
        if (stringToSearch == null || stringToSearch.equals("")) return Collections.emptyList();
        List<Product> productList = productRepository.findByNameContainingIgnoreCaseOrSkuIgnoreCase(stringToSearch, stringToSearch);

        List<SearchPublicResultResponse> resultResponseList = new ArrayList<>();
        for (Product prod : productList){
            String imageUrl = "";
            if (prod.getImages().getFirst().url() == null || prod.getImages().getFirst().url().equals("") ){
                imageUrl = URL_IMAGE_DEFAULT;
            } else {
                imageUrl = prod.getImages().getFirst().url();
            }
            SearchPublicResultResponse response = new SearchPublicResultResponse(
                    prod.getSku(),
                    prod.getName(),
                    prod.getShortDescription(),
                    imageUrl
            );
            resultResponseList.add(response);
        }

        return resultResponseList;
    }

    public List<FeaturedProductResponse> getProductsFeatured() {
        List<Product> productList = productRepository.findByIsFeaturedTrue();
        return productList.stream()
                .map(product -> new FeaturedProductResponse(
                        product.getSku(),
                        product.getImages() == null || product.getImages().isEmpty() ? URL_IMAGE_DEFAULT : product.getImages().getFirst().url() ,
                        product.getName(),
                        product.getShortDescription(),
                        product.getColors(),
                        product.getInventory().stream().map(item-> item.quantity()).reduce(0,(a, b) -> a+b),
                        product.getCollection()
                )).toList();
    }

    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySkuIgnoreCase(sku.trim())
                .orElseThrow(() -> new ProductException("Peça não encontrada no acervo!", HttpStatus.NOT_FOUND));

        List<String> imagesUrlsList = new ArrayList<>();

        if (product.getImages() != null && !product.getImages().isEmpty()){
            imagesUrlsList = product.getImages().stream()
                    .map(productImage -> productImage.url())
                    .toList();
        }

        return new ProductResponse(
                product.getSku(),
                product.getName(),
                product.getShortDescription(),
                product.getFullDescription(),
                product.getPrice(),
                imagesUrlsList,
                product.getColors(),
                product.getInventory(),
                product.getCollection(),
                product.isFeatured()
        );
    }

    public List<ProductResponse> getProductsFromCollection(ProductCollection collection){
        List<Product> productList = productRepository.findByCollection(collection);
        List<ProductResponse> productResponse = this.verifyIfHasImage(productList, 6);
        return productResponse;
    }

    public Page<ProductResponse> getCollectionPaged(ProductCollection collection, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> productPage = productRepository.findByCollection(collection, pageable);

        Page<ProductResponse> productResponsePage = productPage.map(p -> new ProductResponse(
                p.getSku(),
                p.getName(),
                p.getShortDescription(),
                null,
                p.getPrice(),
                p.getImages().isEmpty() ? Collections.emptyList() : List.of(p.getImages().get(0).url()),
                p.getColors(),
                Collections.emptyList(),
                p.getCollection(),
                p.isFeatured()));

        return productResponsePage;
    }

    private List<ProductResponse> verifyIfHasImage(List<Product> productList, Integer limit){
        List<ProductResponse> productResponseList = new ArrayList<>();
        for (Product product : productList){
            if (product.getImages() == null || product.getImages().isEmpty()){
                product.getImages().add(new ProductImage(URL_IMAGE_DEFAULT, ""));
            }
            ProductResponse productResponse = new ProductResponse(
                    product.getSku(),
                    product.getName(),
                    product.getShortDescription(),
                    null,
                    product.getPrice(),
                    product.getImages().isEmpty() ? Collections.emptyList() : List.of(product.getImages().get(0).url()),
                    product.getColors(),
                    Collections.emptyList(),
                    product.getCollection(),
                    product.isFeatured()
            );
            productResponseList.add(productResponse);
        }

        if (limit != null && limit > 0){
            List<ProductResponse> responseListLimited = productResponseList.stream().limit(limit).toList();
            return  responseListLimited;
        }
        return productResponseList;
    }
}
