package com.app.wishlist.dto;

import com.app.wishlist.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClientDtoTest {

    @Mock
    private Client clientMock;
    @Mock
    private ClientDto clientDtoMock;

    @InjectMocks
    private ClientDto.RepresentationBuilder representationBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenToRepresentation() {
        when(clientMock.getId()).thenReturn("1");
        when(clientMock.getName()).thenReturn("Joao da Silva");
        when(clientMock.getCpf()).thenReturn("927.907.020-81");
        when(clientMock.getAddress()).thenReturn("Rua da Wishlist");

        final ClientDto dto = representationBuilder.toRepresentation(clientMock);

        assertNotNull(dto);
        assertEquals(clientMock.getId(), dto.getId());
        assertEquals(clientMock.getName(), dto.getName());
        assertEquals(clientMock.getCpf(), dto.getCpf());
        assertEquals(clientMock.getAddress(), dto.getAddress());

        verify(clientMock, times(2)).getId();
        verify(clientMock, times(2)).getName();
        verify(clientMock, times(2)).getCpf();
        verify(clientMock, times(2)).getAddress();
    }

    @Test
    void whenFromRepresentation() {
        when(clientDtoMock.getId()).thenReturn("1");
        when(clientDtoMock.getName()).thenReturn("Joao da Silva");
        when(clientDtoMock.getCpf()).thenReturn("927.907.020-81");
        when(clientDtoMock.getAddress()).thenReturn("Rua da Wishlist");

        final Client client = representationBuilder.fromRepresentation(clientDtoMock);

        assertNotNull(client);
        assertEquals(clientDtoMock.getId(), client.getId());
        assertEquals(clientDtoMock.getName(), client.getName());
        assertEquals(clientDtoMock.getCpf(), client.getCpf());
        assertEquals(clientDtoMock.getAddress(), client.getAddress());

        verify(clientDtoMock, times(2)).getId();
        verify(clientDtoMock, times(2)).getName();
        verify(clientDtoMock, times(2)).getCpf();
        verify(clientDtoMock, times(2)).getAddress();
    }
}