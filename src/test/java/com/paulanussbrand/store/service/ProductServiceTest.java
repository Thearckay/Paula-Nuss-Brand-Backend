package com.paulanussbrand.store.service;

import com.paulanussbrand.store.dto.ProductCatalogAdmResponse;
import com.paulanussbrand.store.dto.ResponseApi;
import com.paulanussbrand.store.entities.Product;
import com.paulanussbrand.store.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void deveRetornarTodosOsProdutos(){
        List<ProductCatalogAdmResponse> catalogAdmResponse = productService.getCatalogForAdm();
    }
}