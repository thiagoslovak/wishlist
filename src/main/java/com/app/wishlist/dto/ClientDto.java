package com.app.wishlist.dto;

import com.app.wishlist.model.Client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientDto {

    private final String id;
    @Size(max = 50)
    @NotBlank(message = "Name invalid")
    private final String name;
    @Size(max = 14)
    @NotBlank(message = "Cpf invalid")
    private final String cpf;
    @Size(max = 100)
    private final String address;

    private ClientDto(String id, String name, String cpf, String address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAddress() {
        return address;
    }

    public static class ClientDtoBuilder {

        private String id;
        private String name;
        private String cpf;
        private String address;

        public ClientDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ClientDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientDtoBuilder cpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public ClientDtoBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ClientDto build() {
            return new ClientDto(this.id, this.name, this.cpf, this.address);
        }
    }

    public static class RepresentationBuilder {

        public ClientDto toRepresentation(final Client client) {
            return new ClientDtoBuilder()
                    .id(client.getId())
                    .name(client.getName())
                    .cpf(client.getCpf())
                    .address(client.getAddress())
                    .build();
        }

        public Client fromRepresentation(final ClientDto dto) {
            return new Client.ClientBuilder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .cpf(dto.getCpf())
                    .address(dto.getAddress())
                    .build();
        }
    }
}