package com.example.products_service;

import com.example.products_service.dao.ProductRepository;
import com.example.products_service.dto.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = new Product("1", "Product1", "Description1", 10.0);
        Product product2 = new Product("2", "Product2", "Description2", 20.0);

        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product2"));
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product("1", "Sample Product", "Sample Description", 10.0);

        Mockito.when(productRepository.findById("1")).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sample Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Sample Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));
    }

    @Test
    void testAddProduct() throws Exception {
        Product product = new Product("1", "Sample Product", "Sample Description", 10.0);

        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\", \"name\":\"Sample Product\", \"description\":\"Sample Description\", \"price\":10.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sample Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Sample Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(10.0));

        verify(productRepository, Mockito.times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product("1", "Updated Product", "Updated Description", 20.0);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\", \"name\":\"Updated Product\", \"description\":\"Updated Description\", \"price\":20.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20.0));


        Mockito.verify(productRepository, Mockito.times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Mockito.doNothing().when(productRepository).deleteById("1");

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productRepository, Mockito.times(1)).deleteById("1");
    }

}
