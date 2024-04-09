package com.deepak.web;

import com.deepak.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class APIBestPracticeController {

    @Operation(summary = "Get all the products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All the products.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)})
    @GetMapping("/products")
    public List<Product> getProducts() {
        return Arrays.asList(Product.builder().id(1).name("Ultraboost M1").price(17999.0).description("Running Shoes").category("Shoes").build(),
                Product.builder().id(2).name("Cap Adidas").price(1299.0).description("Casual Cap").category("Cap").build());
    }

    @Operation(summary = "Get a product by it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product by its id.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable String id) {
        return null;
    }

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a product",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "409", description = "Product already present.", content = @Content)})
    @PostMapping
    public Product createProduct() {
        return null;
    }

    @Operation(summary = "Delete a product by it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a product",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @DeleteMapping
    public Product deleteProduct() {
        return null;
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})

    @PutMapping
    public Product updateProduct(){
        return null;
    }

    @Operation(summary = "Patch a product by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @PatchMapping
    public Product patchProduct() {
        return null;
    }

}
