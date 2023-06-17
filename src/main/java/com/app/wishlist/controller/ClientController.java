package com.app.wishlist.controller;

import com.app.wishlist.dto.ClientDto;
import com.app.wishlist.model.Client;
import com.app.wishlist.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    private final ClientDto.RepresentationBuilder builder = new ClientDto.RepresentationBuilder();

    @GetMapping
    public List<ClientDto> findingAll() {
        List<Client> clients = clientService.findingAllClient();

        return clients.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findingById(@PathVariable final String id) {
        final Client client = clientService.findingClientById(id);

        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(client));
    }

    @PostMapping
    public ResponseEntity<ClientDto> creating(@RequestBody @Valid final ClientDto dto) {
        final Client client = clientService.creatingClient(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(client));
    }

    @PutMapping
    public ResponseEntity<ClientDto> updating(@RequestBody @Valid final ClientDto dto) {
        final Client client = clientService.updatingClient(builder.fromRepresentation(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(client));
    }

    @DeleteMapping("/{id}")
    public void deleting(@PathVariable final String id) {
        clientService.deletingClient(id);
    }
}