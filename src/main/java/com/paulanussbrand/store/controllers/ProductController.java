package com.paulanussbrand.store.controllers;

import com.paulanussbrand.store.dto.*;
import com.paulanussbrand.store.entities.Product;
import com.paulanussbrand.store.enums.ProductCollection;
import com.paulanussbrand.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseApi> registerProduct(
            @RequestPart("productData") ProductRequest productRequest,
            @RequestPart(value = "galleryImages", required = false) List<MultipartFile> images
            ){
        Product productSaved = productService.createProduct(productRequest, images);

        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productSaved,
                "Produto registrado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/featured")
    public ResponseEntity<ResponseApi> getProductsFeatured(){
        List<FeaturedProductResponse> productResponseList = productService.getProductsFeatured();

        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productResponseList,
                "Lista de destaques retornado",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/catalog/{sku}")
    public ResponseEntity<ResponseApi> getProductBySku(@PathVariable("sku") String sku){
        ProductResponse productResponse = productService.getProductBySku(sku);

        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productResponse,
                "Produto retornado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseApi> getCatalogForAdm(){
        List<ProductCatalogAdmResponse> catalogAdmResponseList = productService.getCatalogForAdm();
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                catalogAdmResponseList,
                "Catalogo retornado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getProductWithId(@PathVariable("id") UUID id){
        Product productToReturn =  productService.getProductWithId(id);

        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productToReturn,
                "Produto retornado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseApi> updateProductWithId(
            @PathVariable("id") UUID id,
            @RequestPart(value = "images", required = false) List<MultipartFile> imagens,
            @RequestPart("product") ProductRequest productUpdated,
            @RequestPart(value = "imagePublicIdToDelete", required = false) List<String> imagePublicIdToDelete
    ){
        Product productSaved = productService.updateProduct(id, productUpdated, imagens, imagePublicIdToDelete );
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productSaved,
                "Produto Atualizado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteProductById(@PathVariable("id") UUID productId){
        productService.deleteProductByid(productId);
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                Collections.emptyList(),
                "Produto deletado com sucesso!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/seller/search")
    public ResponseEntity<ResponseApi> searchProductForAdm(@RequestParam("query") String nameToSearch){
        List<SearchResultResponse> searchResultResponseList = productService.searchProduct(nameToSearch);
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                searchResultResponseList,
                "Procura feita!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam("query") String query){
        List<SearchPublicResultResponse> searchResultResponseList = productService.searchPublicProduct(query);
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                searchResultResponseList,
                "Procura feita!",
                LocalDateTime.now()
        ));
    }


    @GetMapping("/essentials")
    public ResponseEntity<ResponseApi> getEssentialsForHome(){
        List<ProductResponse> productResponseList = productService.getProductsFromCollection(ProductCollection.ESSENTIALS);
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productResponseList,
                "Produtos Essentials retornado!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/basics")
    public ResponseEntity<ResponseApi> getBasicsForHome(){
        List<ProductResponse> productResponseList = productService.getProductsFromCollection(ProductCollection.BASICS);
        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productResponseList,
                "Produtos Basics retornado!",
                LocalDateTime.now()
        ));
    }

    @GetMapping("/catalog/collection/{collection}")
    public ResponseEntity<ResponseApi> getEssentialsPageable(
            @PathVariable(value = "collection") String collection,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){

        ProductCollection category = ProductCollection.valueOf(collection.toUpperCase());
        Page<ProductResponse> productResponseList = productService.getCollectionPaged(category, page, size);

        return ResponseEntity.ok(new ResponseApi(
                HttpStatus.OK.value(),
                productResponseList,
                "Produtos "+collection+" retornado!",
                LocalDateTime.now()
        ));
    }

}
