package com.app.wishlist.service;

import com.app.wishlist.model.Client;
import com.app.wishlist.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepositoryMock;
    @Mock
    private Client clientMock;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindingAllClient(){
        when(clientRepositoryMock.findAll()).thenReturn(Collections.singletonList(clientMock));

        final List<Client> clients = clientService.findingAllClient();

        assertNotNull(clients);
        assertFalse(clients.isEmpty());

        verify(clientRepositoryMock).findAll();
    }

    @Test
    void whenFindingClientById() {
        when(clientMock.getId()).thenReturn("1");
        when(clientMock.getName()).thenReturn("Joao da Silva");
        when(clientMock.getCpf()).thenReturn("927.907.020-81");
        when(clientMock.getAddress()).thenReturn("Rua da Wishlist");
        when(clientRepositoryMock.findById("1")).thenReturn(Optional.of(clientMock));

        final Client client = clientService.findingClientById("1");

        assertNotNull(client);
        assertEquals(clientMock.getId(), client.getId());
        assertEquals(clientMock.getName(), client.getName());
        assertEquals(clientMock.getCpf(), client.getCpf());
        assertEquals(clientMock.getAddress(), client.getAddress());

        verify(clientMock, times(2)).getId();
        verify(clientMock, times(2)).getName();
        verify(clientMock, times(2)).getCpf();
        verify(clientMock, times(2)).getAddress();
        verify(clientRepositoryMock).findById("1");
    }

    @Test
    void whenFindingClientByIdReturnException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clientService.findingClientById(null);
        });

        assertNotNull(exception);
        assertEquals("The client does not exist.", exception.getMessage());

        verify(clientRepositoryMock).findById(any());
    }

    @Test
    void whenCreatingClient() {
        when(clientRepositoryMock.save(clientMock)).thenReturn(clientMock);

        final Client client = clientService.creatingClient(clientMock);

        assertNotNull(client);

        verify(clientRepositoryMock).save(clientMock);
    }

    @Test
    void whenCreatingClientAndClientIsEquals() {
        when(clientMock.getId()).thenReturn("1");
        when(clientMock.getName()).thenReturn("Joao da Silva");
        when(clientMock.getCpf()).thenReturn("927.907.020-81");
        when(clientMock.getAddress()).thenReturn("Rua da Wishlist");

        when(clientRepositoryMock.findByCpf("927.907.020-81")).thenReturn(clientMock);

        final Client client = clientService.creatingClient(clientMock);

        assertNotNull(client);
        assertEquals(clientMock.getId(), client.getId());
        assertEquals(clientMock.getName(), client.getName());
        assertEquals(clientMock.getCpf(), client.getCpf());
        assertEquals(clientMock.getAddress(), client.getAddress());

        verify(clientMock, times(2)).getId();
        verify(clientMock, times(2)).getName();
        verify(clientMock, times(6)).getCpf();
        verify(clientMock, times(2)).getAddress();

        verify(clientRepositoryMock, times(2)).findByCpf("927.907.020-81");
    }

    @Test
    void whenCreatingClientReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clientService.creatingClient(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenUpdatingClient() {
        when(clientMock.getId()).thenReturn("1");

        when(clientRepositoryMock.findById("1")).thenReturn(Optional.of(clientMock));
        when(clientRepositoryMock.save(clientMock)).thenReturn(clientMock);

        final Client client = clientService.updatingClient(clientMock);

        assertNotNull(client);
        assertEquals(clientMock.getId(), client.getId());

        verify(clientMock, times(3)).getId();

        verify(clientRepositoryMock).findById("1");
        verify(clientRepositoryMock).save(clientMock);
    }

    @Test
    void whenUpdatingClientReturnException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clientService.updatingClient(null);
        });

        assertNotNull(exception);
    }

    @Test
    void whenDeletingClient() {
        when(clientRepositoryMock.findById("1")).thenReturn(Optional.of(clientMock));

        clientService.deletingClient("1");

        verify(clientRepositoryMock).findById("1");
        verify(clientRepositoryMock).deleteById("1");
    }

    @Test
    void whenCheckingEqualClientReturnTrue() {
        when(clientMock.getCpf()).thenReturn("927.907.020-81");

        when(clientRepositoryMock.findByCpf("927.907.020-81")).thenReturn(clientMock);

        boolean response = clientService.checkingEqualClient(clientMock);

        assertTrue(response);

        verify(clientMock, times(3)).getCpf();

        verify(clientRepositoryMock).findByCpf("927.907.020-81");
    }

    @Test
    void whenCheckingEqualClientReturnFalse() {
        boolean response = clientService.checkingEqualClient(clientMock);

        assertFalse(response);
    }
}