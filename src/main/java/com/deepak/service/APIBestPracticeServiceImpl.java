package com.deepak.service;

import com.deepak.exception.ProductAlreadyExistException;
import com.deepak.exception.ProductNotFoundException;
import com.deepak.model.Product;
import com.deepak.repository.APIBestPracticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Service
public class APIBestPracticeServiceImpl implements APIBestPracticeService {

    private final APIBestPracticeRepository apiBestPracticeRepository;

    public APIBestPracticeServiceImpl(APIBestPracticeRepository apiBestPracticeRepository) {
        this.apiBestPracticeRepository = apiBestPracticeRepository;
    }

    @Override
    public List<Product> products(Optional<Integer> page, Optional<Integer> size, String[] orderBy,
                                  Map<String, String> filter) {
        List<Product> products = new ArrayList<>();

        Sort sort = constructSortCriteria(Arrays.asList(orderBy != null ? orderBy : new String[]{}));

        // Pagination and Sorting
        Pageable pageable = PageRequest.of(page.orElseGet(() -> 0), size.orElseGet(() -> Integer.MAX_VALUE), sort);
        Page<Product> all = apiBestPracticeRepository.findAll(filterByFields(filter), pageable);
        apiBestPracticeRepository.findAll(filterByFields(filter), pageable).forEach(products::add);
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        Optional<Product> optionalProduct = apiBestPracticeRepository.findById(product.getId());
        optionalProduct.ifPresent(value -> {
            throw new ProductAlreadyExistException("Product with this id " + value.getId() + " already exists");
        });
        return apiBestPracticeRepository.save(product);
    }

    @Override
    public Product getProductById(Integer id) {
        UnaryOperator<String> message = value -> "Product with id " + value + " was not found.";
        return apiBestPracticeRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(message.apply(String.valueOf(id))));
    }

    @Override
    public void deleteProductById(Integer id) {
        UnaryOperator<String> message = value -> "Product with id " + value + " was not found.";
        apiBestPracticeRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(message.apply(String.valueOf(id))));
        apiBestPracticeRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        UnaryOperator<String> message = value -> "Product with id " + value + " was not found.";
        Optional<Product> productOptional = apiBestPracticeRepository.findById(product.getId());
        Product updatedProduct = productOptional.map(value -> value.toBuilder().id(product.getId())
                        .price(product.getPrice() == null ? value.getPrice() : product.getPrice())
                        .name(product.getName() == null ? value.getName() : product.getName())
                        .description(product.getDescription() == null ? value.getDescription() : product.getDescription())
                        .category(product.getCategory() == null ? value.getCategory() : product.getCategory())
                        .build())
                .orElseThrow(() -> new ProductNotFoundException(message.apply(String.valueOf(product.getId()))));
        return apiBestPracticeRepository.save(updatedProduct);
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

    private Specification<Product> filterByFields(Map<String, String> filter) {
        return (root, query, criteriaBuild) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Map.Entry<String, String> entry : filter.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value != null && !value.isEmpty()) {
                    if (Arrays.stream(Product.class.getDeclaredFields()).map(a -> a.getName()).collect(Collectors.toList())
                            .contains(key)) {
                        predicates.add(criteriaBuild.like(root.get(key), "%" + value + "%"));
                    }
                }
            }
            return criteriaBuild.and(predicates.toArray(new Predicate[0]));
        };
    }
}
