package com.deepak.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;

//@JsonFilter("ProductFilter")
@Entity(name = "products")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    Integer id;
    String name;
    String description;
    Double price;
    String category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
