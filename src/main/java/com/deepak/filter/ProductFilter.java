package com.deepak.filter;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.stereotype.Component;

//:TODO To be fixed

@Component
public class ProductFilter {

    public SimpleFilterProvider provideFilter(String... fields) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(fields);
        return new SimpleFilterProvider().addFilter("ProductFilter", filter);
    }
}