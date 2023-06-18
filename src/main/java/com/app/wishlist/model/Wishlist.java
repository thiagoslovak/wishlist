package com.app.wishlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Document(collection = "WL_WISHLISTS")
public class Wishlist {

    @Id
    private final String id;
    @NotBlank(message = "Name invalid")
    private final String name;
    @DBRef
    private final List<Product> products;
    @DBRef
    @Indexed(unique = true)
    private final Client client;

    private Wishlist(String id, String name, List<Product> products, Client client) {
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

    public List<Product> getProducts() {
        return products;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", client=" + client +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wishlist wishlist = (Wishlist) o;
        return Objects.equals(id, wishlist.id) && Objects.equals(name, wishlist.name) && Objects.equals(products, wishlist.products) && Objects.equals(client, wishlist.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, products, client);
    }

    public static class WishlistBuilder {
        private String id;
        private String name;
        private List<Product> products;
        private Client client;

        public WishlistBuilder id(String id) {
            this.id = id;
            return this;
        }

        public WishlistBuilder name(String name) {
            this.name = name;
            return this;
        }

        public WishlistBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        public WishlistBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this.id, this.name, this.products, this.client);
        }
    }
}