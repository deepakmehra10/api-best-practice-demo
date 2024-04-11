package com.deepak.repository;

import com.deepak.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIBestPracticeRepository extends PagingAndSortingRepository<Product, Integer> {
}
