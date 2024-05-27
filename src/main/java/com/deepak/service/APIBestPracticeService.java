package com.deepak.service;

import com.deepak.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface APIBestPracticeService {

    public List<Product> products(Optional<Integer> page, Optional<Integer> size, String[] orderBy, Map<String, String> filter);

    Product createProduct(Product product);

    Product getProductById(Integer id);

    void deleteProductById(Integer id);

    Product updateProduct(Product product);
}
