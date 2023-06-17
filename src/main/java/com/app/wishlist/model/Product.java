package com.app.wishlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "WL_PRODUCTS")
public class Product {

    @Id
    private final String id;
    @NotBlank(message = "Name invalid")
    private final String name;
    private final String color;
    private final BigDecimal value;
    private final String details;

    private Product(String id, String name, String color, BigDecimal value, String details) {
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

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", value=" + value +
                ", details='" + details + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(color, that.color) && Objects.equals(value, that.value) && Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, value, details);
    }

    public static class ProductBuilder {

        private String id;
        private String name;
        private String color;
        private BigDecimal value;
        private String details;

        public ProductBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder color(String color) {
            this.color = color;
            return this;
        }

        public ProductBuilder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public ProductBuilder details(String details) {
            this.details = details;
            return this;
        }

        public Product build() {
            return new Product(this.id, this.name, this.color, this.value, this.details);
        }
    }
}