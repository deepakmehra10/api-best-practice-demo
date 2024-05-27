package com.deepak.repository;

import com.deepak.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIBestPracticeRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
}
