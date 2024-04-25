package com.deepak.web;

import com.deepak.annotation.CustomFilter;
import com.deepak.model.Product;
import com.deepak.service.APIBestPracticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1")
public class APIBestPracticeController {

    @Autowired
    private APIBestPracticeService apiBestPracticeService;

    @Operation(summary = "Get all the products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All the products.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)})
    @GetMapping("/products")
    @CustomFilter(value = "filterName")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(required = false) String[] orderBy,
                                                     @RequestParam(required = false) Map<String, String> filter) {

        List<Product> products = apiBestPracticeService.products(Optional.ofNullable(page),
                Optional.ofNullable(size), orderBy, filter);
        return ResponseEntity.ok().body(products);
    }

    @Operation(summary = "Get a product by it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product by its id.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(apiBestPracticeService.getProductById(id));
    }

    @Operation(summary = "Create a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a product",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "409", description = "Product already present.", content = @Content)})
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiBestPracticeService.createProduct(product));
    }

    @Operation(summary = "Delete a product by it's id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a product",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        apiBestPracticeService.deleteProductById(id);
        return ResponseEntity.ok().body("Successfully deleted the product with Id: " + id);
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})

    @PutMapping("/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        return ResponseEntity.ok().body(apiBestPracticeService.updateProduct(product));
    }

    @Operation(summary = "Patch a product by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @PatchMapping("/products")
    public ResponseEntity<Product> patchProduct(@RequestBody Product product) {
        return ResponseEntity.ok().body(apiBestPracticeService.updateProduct(product));
    }

}
