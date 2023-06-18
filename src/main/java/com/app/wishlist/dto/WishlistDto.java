package com.app.wishlist.dto;

import com.app.wishlist.model.Client;
import com.app.wishlist.model.Product;
import com.app.wishlist.model.Wishlist;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class WishlistDto {

    private final String id;
    @Size(max = 50)
    @NotBlank(message = "Name invalid")
    private final String name;
    @Size(max = 20)
    private final List<Product> products;
    private final Client client;

    private WishlistDto(String id, String name, List<Product> products, Client client) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Product> getProducts() {
        return products;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Client getClient() {
        return client;
    }

    public static class WishlistDtoBuilder {
        private String id;
        private String name;
        private List<Product> products;
        private Client client;

        public WishlistDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public WishlistDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WishlistDtoBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public WishlistDtoBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public WishlistDto build() {
            return new WishlistDto(this.id, this.name, this.products, this.client);
        }
    }

    public static class RepresentationBuilder {

        public WishlistDto toRepresentation(final Wishlist wishlist) {
            return new WishlistDtoBuilder()
                    .id(wishlist.getId())
                    .name(wishlist.getName())
                    .products(wishlist.getProducts())
                    .client(wishlist.getClient())
                    .build();
        }

        public Wishlist fromRepresentation(final WishlistDto dto) {
            return new Wishlist.WishlistBuilder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .products(dto.getProducts())
                    .client(dto.getClient())
                    .build();
        }
    }
}