package com.example.EcomApp;

import com.example.EcomApp.pojo.Product;
import com.example.EcomApp.repository.ProductRepository;
import com.example.EcomApp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks and injects them into the service
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(),
                new Product()
        );
        when(repository.findAll()).thenReturn(products); // Mocks the repository to return a predefined list of products

        List<Product> result = service.getAllProducts();

        assertEquals(products, result);
        verify(repository).findAll(); // Verifies that the repository's findAll method was called
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() {
        Product product = new Product();
        when(repository.findById(1)).thenReturn(Optional.of(product)); // Mocks the repository to return a product for the given ID

        Product result = service.getProductById(1);

        assertEquals(product, result);
        verify(repository).findById(1); // Verifies that the repository's findById method was called with the correct ID
    }

    @Test
    void getProductById_WhenProductDoesNotExist_ShouldReturnNull() {
        when(repository.findById(1)).thenReturn(Optional.empty()); // Mocks the repository to return an empty Optional for the given ID

        Product result = service.getProductById(1);

        assertNull(result);
        verify(repository).findById(1); // Verifies that the repository's findById method was called with the correct ID
    }

    @Test
    void addProduct_ShouldSaveProductWithImage() throws IOException {
        Product product = new Product();
        byte[] imageData = "test".getBytes();

        when(imageFile.getBytes()).thenReturn(imageData); // Mocks the image file to return predefined byte data
        when(imageFile.getOriginalFilename()).thenReturn("test.jpg"); // Mocks the image file's original filename
        when(imageFile.getContentType()).thenReturn("image/jpeg"); // Mocks the image file's content type
        when(repository.save(any(Product.class))).thenReturn(product); // Mocks the repository to return the saved product

        Product result = service.addProduct(product, imageFile);

        assertEquals(product, result);
        assertEquals("test.jpg", product.getImageName()); // Verifies that the product's image name is set correctly
        assertEquals("image/jpeg", product.getImageType()); // Verifies that the product's image type is set correctly
        assertArrayEquals(imageData, product.getImageDate()); // Verifies that the product's image data is set correctly
        verify(repository).save(product); // Verifies that the repository's save method was called with the correct product
    }

    @Test
    void updateProduct_ShouldUpdateProductWithNewImage() throws IOException {
        Product product = new Product();
        byte[] imageData = "test".getBytes();

        when(imageFile.getBytes()).thenReturn(imageData); // Mocks the image file to return predefined byte data
        when(imageFile.getOriginalFilename()).thenReturn("updated.jpg"); // Mocks the image file's updated filename
        when(imageFile.getContentType()).thenReturn("image/jpeg"); // Mocks the image file's content type
        when(repository.save(any(Product.class))).thenReturn(product); // Mocks the repository to return the updated product

        Product result = service.updateProduct(1, product, imageFile);

        assertEquals(product, result);
        assertEquals("updated.jpg", product.getImageName()); // Verifies that the product's image name is updated correctly
        assertEquals("image/jpeg", product.getImageType()); // Verifies that the product's image type is updated correctly
        assertArrayEquals(imageData, product.getImageDate()); // Verifies that the product's image data is updated correctly
        verify(repository).save(product); // Verifies that the repository's save method was called with the correct product
    }

    @Test
    void deleteProduct_ShouldDeleteProduct() {
        doNothing().when(repository).deleteById(1); // Mocks the repository to do nothing when deleteById is called

        service.deleteProduct(1);

        verify(repository).deleteById(1); // Verifies that the repository's deleteById method was called with the correct ID
    }

    @Test
    void searchProducts_ShouldReturnMatchingProducts() {
        List<Product> products = Arrays.asList(new Product());
        when(repository.searchProducts("test")).thenReturn(products); // Mocks the repository to return a list of products matching the keyword

        List<Product> result = service.searchProducts("test");

        assertEquals(products, result);
        verify(repository).searchProducts("test"); // Verifies that the repository's searchProducts method was called with the correct keyword
    }
}