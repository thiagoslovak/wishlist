package com.app.wishlist.controller;

import com.app.wishlist.dto.ClientDto;
import com.app.wishlist.model.Client;
import com.app.wishlist.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
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

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    private final ClientDto.RepresentationBuilder builder = new ClientDto.RepresentationBuilder();

    @GetMapping
    @Operation(summary = "Realiaza a busca de clientes.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca dos clientes.")
    })
    public List<ClientDto> findingAll() {
        log.info("Buscando dados de todos os clientes.");
        List<Client> clients = clientService.findingAllClient();
        return clients.stream().map(builder::toRepresentation).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiaza a busca de cliente por id.", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a busca do cliente.")
    })
    public ResponseEntity<ClientDto> findingById(@PathVariable final String id) {
        log.info(format("Buscando dados do cliente com id: %s.", id));
        final Client client = clientService.findingClientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(builder.toRepresentation(client));
    }

    @PostMapping
    @Operation(summary = "Realiaza a criação de cliente.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a criação do cliente.")
    })
    public ResponseEntity<ClientDto> creating(@RequestBody @Valid final ClientDto dto) {
        log.info("Criação do cliente iniciado!");
        final Client client = clientService.creatingClient(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(client));
    }

    @PutMapping
    @Operation(summary = "Realiaza a atualização de cliente.", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a atualização do cliente.")
    })
    public ResponseEntity<ClientDto> updating(@RequestBody @Valid final ClientDto dto) {
        log.info("Atualização do cliente iniciado!");
        final Client client = clientService.updatingClient(builder.fromRepresentation(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(builder.toRepresentation(client));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Realiaza a remoção de cliente.", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a remoção do cliente.")
    })
    public ResponseEntity<Object> deleting(@PathVariable final String id) {
        clientService.deletingClient(id);
        log.info(format("Cliente com id: %s, deletado.", id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}