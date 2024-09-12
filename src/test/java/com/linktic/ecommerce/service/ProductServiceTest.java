package com.linktic.ecommerce.service;

import com.linktic.ecommerce.model.Product;
import com.linktic.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;


class ProductServiceTest {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "Laptop", "High performance laptop", 1200.00);
        Product savedProduct = new Product(1L, "Laptop", "High performance laptop", 1200.00);

        given(productRepository.save(product)).willReturn(savedProduct);

        Product result = productService.createProduct(product);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product(1L, "Laptop", "High performance laptop", 1200.00);
        Product product2 = new Product(2L, "Phone", "Latest model smartphone", 800.00);
        List<Product> productList = Arrays.asList(product1, product2);

        given(productRepository.findAll()).willReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Laptop");
        assertThat(result.get(1).getName()).isEqualTo("Phone");
    }
}
