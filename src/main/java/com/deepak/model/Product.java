package com.deepak.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "products")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    Integer id;
    String name;
    String description;
    Double price;
    String category;
}
