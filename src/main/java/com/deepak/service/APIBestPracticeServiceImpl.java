package com.deepak.service;

import com.deepak.model.Product;
import com.deepak.repository.APIBestPracticeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class APIBestPracticeServiceImpl implements APIBestPracticeService {

    private final APIBestPracticeRepository apiBestPracticeRepository;

    public APIBestPracticeServiceImpl(APIBestPracticeRepository apiBestPracticeRepository) {
        this.apiBestPracticeRepository = apiBestPracticeRepository;
    }

    @Override
    public List<Product> products(Optional<Integer> page, Optional<Integer> size, String[] orderBy) {
        List<Product> products = new ArrayList<>();
        Sort sort = constructSortCriteria(Arrays.asList(orderBy));

        // Pagination and Sorting
        Pageable pageable = PageRequest.of(page.orElseGet(() -> 0), size.orElseGet(() -> Integer.MAX_VALUE), sort);
        apiBestPracticeRepository.findAll(pageable).forEach(products::add);
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return apiBestPracticeRepository.save(product);
    }

    @Override
    public Product getProductById(Integer id) {
        return apiBestPracticeRepository.findById(id).orElseThrow(() -> new RuntimeException("Product with this user id not found!"));
    }

    @Override
    public void deleteProductById(Integer id) {
        apiBestPracticeRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        return apiBestPracticeRepository.save(product);
    }

    private Sort constructSortCriteria(List<String> fields) {
        List<Sort.Order> orders = new ArrayList<>();
        if (fields != null && !fields.isEmpty()) {
            for (String field : fields) {
                if (isValidField(field)) {
                    if (field.startsWith("+")) {
                        String fieldName = field.substring(1);
                        orders.add(Sort.Order.asc(fieldName));
                    } else if (field.startsWith("-")) {
                        String fieldName = field.substring(1);
                        orders.add(Sort.Order.desc(fieldName));
                    } else {
                        orders.add(Sort.Order.asc(field));
                    }
                }
            }
        }
        return Sort.by(orders);
    }

    private boolean isValidField(String field) {
        // Remove prefix if present
        String fieldName = field.startsWith("+") || field.startsWith("-") ? field.substring(1) : field;
        // Get all fields of the entity class
        Field[] entityFields = Product.class.getDeclaredFields();
        // Check if the provided field exists in the entity class
        return Arrays.stream(entityFields)
                .map(Field::getName)
                .anyMatch(fieldName::equals);
    }
}
