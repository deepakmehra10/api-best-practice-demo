package com.deepak.filter;

import com.deepak.annotation.CustomFilter;
import com.deepak.model.Product;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
//:TODO To be fixed
@ControllerAdvice
public class FilteringResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {
        @Override
        protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
                                               MethodParameter returnType, ServerHttpRequest request,
                                               ServerHttpResponse response) {
            if (returnType.hasMethodAnnotation(CustomFilter.class)) {
                CustomFilter jsonFilterAnnotation = returnType.getMethodAnnotation(CustomFilter.class);
                String filterName = jsonFilterAnnotation.value();
                if (!filterName.isEmpty()) {
                    SimpleFilterProvider filterProvider = new SimpleFilterProvider()
                            .addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(getSelectedFields(filterName)));
                    bodyContainer.setFilters(filterProvider);
                }
            }
        }

        private String[] getSelectedFields(String filterName) {
            // Implement logic to determine which fields to include based on filterName
            // For demonstration, let's return a hardcoded list of fields
            if ("filterName".equals(filterName)) {
                return new String[]{"name"};
            }
            // If filterName is not recognized, return an empty array or handle it accordingly
            return new String[]{};
        }
    }


