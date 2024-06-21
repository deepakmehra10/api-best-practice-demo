package com.deepak.service;

import com.deepak.exception.ProductAlreadyExistException;
import com.deepak.exception.ProductNotFoundException;
import com.deepak.model.Product;
import com.deepak.repository.APIBestPracticeRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class APIBestPracticeServiceImplTest {

    @Mock
    APIBestPracticeRepository apiBestPracticeRepository;

    @InjectMocks
    APIBestPracticeServiceImpl apiBestPracticeService;

    //: TODO will work on this in coming iterations
    @Ignore
    @Test
    public void shouldReturnProducts() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Product product2 = Product.builder()
                .id(2)
                .name("Aeroready")
                .price(1299.0)
                .category("Clothing")
                .description("Casual T-shirt")
                .build();

        apiBestPracticeService.products(Optional.empty(), Optional.empty(), null, null);
        Iterable<Product> productIterable = Arrays.asList(product1, product2).stream()::iterator;
    }

    @Test
    public void shouldReturnProductById() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.of(product1));
        Product actualResult = apiBestPracticeService.getProductById(1);
        assertThat(actualResult, is(equalTo(product1)));
    }

    @Test
    public void shouldThrowExceptionProductNotFound() {
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.empty());
        try {
            apiBestPracticeService.getProductById(1);
            fail("The flow should not reach here");
        } catch (ProductNotFoundException exception) {
            assertThat(exception, is(instanceOf(ProductNotFoundException.class)));
            assertThat(exception.getMessage(), is(equalTo("Product with id 1 was not found.")));
        }
    }

    @Test
    public void shouldCreateProduct() {
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.empty());
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Mockito.when(apiBestPracticeRepository.save(product1)).thenReturn(product1);
        Product actualResult = apiBestPracticeService.createProduct(product1);
        assertThat(actualResult, is(equalTo(product1)));
    }

    @Test
    public void shouldThrowProductAlreadyExistsException() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        try {
            apiBestPracticeService.createProduct(product1);
            fail("The flow should not reach here");
        } catch (ProductAlreadyExistException productAlreadyExistException) {
            assertThat(productAlreadyExistException, is(instanceOf(ProductAlreadyExistException.class)));
            assertThat(productAlreadyExistException.getMessage(), is(equalTo("Product with this id 1 already exists")));
        }
    }

    @Test
    public void shouldUpdateProduct() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Product updatedProduct = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost M1")
                .description("Running Shoes")
                .category("Shoes")
                .build();

        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        Mockito.when(apiBestPracticeRepository.save(updatedProduct)).thenReturn(updatedProduct);
        Product actualResult = apiBestPracticeService.updateProduct(updatedProduct);
        assertThat(actualResult, is(equalTo(updatedProduct)));
    }

    @Test
    public void shouldThrowProductNotFoundWhileUpdating() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.empty());
        try {
            apiBestPracticeService.updateProduct(product1);
            fail("The flow should not reach here");
        } catch (ProductNotFoundException productNotFoundException) {
            assertThat(productNotFoundException, is(instanceOf(ProductNotFoundException.class)));
            assertThat(productNotFoundException.getMessage(), is(equalTo("Product with id 1 was not found.")));
        }
    }

    @Test
    public void shouldDeleteProductById() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        apiBestPracticeService.deleteProductById(1);
        Mockito.verify(apiBestPracticeRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void shouldThrowExceptionWhileDeletingProduct() {
        Mockito.when(apiBestPracticeRepository.findById(1)).thenReturn(Optional.empty());
        try {
            apiBestPracticeService.deleteProductById(1);
            fail("The flow should not reach here");
        } catch (ProductNotFoundException productNotFoundException) {
            assertThat(productNotFoundException, is(instanceOf(ProductNotFoundException.class)));
            assertThat(productNotFoundException.getMessage(), is(equalTo("Product with id 1 was not found.")));
        }
    }

    @Test
    public void shouldPatchProduct() {
        Product productToBeUpdated = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost1")
                .description("Running Shoes")
                .category("Shoes")
                .build();

        Product existingProduct = Product.builder()
                .id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();

        Mockito.when(apiBestPracticeRepository.findById(productToBeUpdated.getId())).thenReturn(Optional.ofNullable(existingProduct));
        Mockito.when(apiBestPracticeRepository.save(productToBeUpdated)).thenReturn(productToBeUpdated);
        Product actualResult = apiBestPracticeService.updateProduct(productToBeUpdated);
        assertThat(actualResult.getId(), is(equalTo(1)));
        assertThat(actualResult.getName(), is(equalTo("Ultraboost1")));
        assertThat(actualResult.getCategory(), is(equalTo("Shoes")));
        assertThat(actualResult.getDescription(), is(equalTo("Running Shoes")));
        assertThat(actualResult.getPrice(), is(equalTo(999.0)));
    }

    @Test
    public void shouldThrowExceptionWhilePatchingProduct() {
        Product product1 = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost")
                .description("Running Shoes")
                .category("Shoes")
                .build();

        Product updatedProduct = Product.builder().id(1)
                .price(999.0)
                .name("Ultraboost1")
                .description("Running Shoes")
                .category("Shoes")
                .build();

        try {
            apiBestPracticeService.updateProduct(updatedProduct);
            fail("The flow should not here");
        } catch (ProductNotFoundException productNotFoundException) {
            assertThat(productNotFoundException.getMessage(), is(equalTo("Product with id 1 was not found.")));
        }
    }
}

