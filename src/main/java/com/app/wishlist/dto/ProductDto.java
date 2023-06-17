package com.app.wishlist.dto;

import com.app.wishlist.model.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductDto {

    private final String id;
    @Size(max = 50)
    @NotBlank(message = "Name invalid")
    private final String name;
    @Size(max = 20)
    private final String color;
    private final BigDecimal value;
    @Size(max = 100)
    private final String details;

    public ProductDto(String id, String name, String color, BigDecimal value, String details) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.value = value;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDetails() {
        return details;
    }

    public static class ProductDtoBuilder {

        private String id;
        private String name;
        private String color;
        private BigDecimal value;
        private String details;

        public ProductDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProductDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductDtoBuilder color(String color) {
            this.color = color;
            return this;
        }

        public ProductDtoBuilder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public ProductDtoBuilder details(String details) {
            this.details = details;
            return this;
        }

        public ProductDto build() {
            return new ProductDto(this.id, this.name, this.color, this.value, this.details);
        }
    }

    public static class RepresentationBuilder {

        public ProductDto toRepresentation(final Product product) {
            return new ProductDtoBuilder()
                    .id(product.getId())
                    .name(product.getName())
                    .color(product.getColor())
                    .value(product.getValue())
                    .details(product.getDetails())
                    .build();
        }

        public Product fromRepresentation(final ProductDto dto) {
            return new Product.ProductBuilder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .color(dto.getColor())
                    .value(dto.getValue())
                    .details(dto.getDetails())
                    .build();
        }
    }
}