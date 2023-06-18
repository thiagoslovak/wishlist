package com.app.wishlist.dto;

import com.app.wishlist.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductDtoTest {

    @Mock
    private Product productMock;
    @Mock
    private ProductDto productDtoMock;

    @InjectMocks
    private ProductDto.RepresentationBuilder representationBuilder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenToRepresentation() {
        when(productMock.getId()).thenReturn("1");
        when(productMock.getName()).thenReturn("Smart TV 43 UHD 4K LG");
        when(productMock.getColor()).thenReturn("Preto");
        when(productMock.getValue()).thenReturn(BigDecimal.valueOf(1499));
        when(productMock.getDetails()).thenReturn("Prepare-se para experienciar um novo nível de imersão televisiva com a TV LG UHD AU7700.");

        final ProductDto dto = representationBuilder.toRepresentation(productMock);

        assertNotNull(dto);
        assertEquals(productMock.getId(), dto.getId());
        assertEquals(productMock.getName(), dto.getName());
        assertEquals(productMock.getColor(), dto.getColor());
        assertEquals(productMock.getValue(), dto.getValue());
        assertEquals(productMock.getDetails(), dto.getDetails());

        verify(productMock, times(2)).getId();
        verify(productMock, times(2)).getName();
        verify(productMock, times(2)).getColor();
        verify(productMock, times(2)).getValue();
        verify(productMock, times(2)).getDetails();
    }

    @Test
    void whenFromRepresentation() {
        when(productDtoMock.getId()).thenReturn("1");
        when(productDtoMock.getName()).thenReturn("Smart TV 43 UHD 4K LG");
        when(productDtoMock.getColor()).thenReturn("Preto");
        when(productDtoMock.getValue()).thenReturn(BigDecimal.valueOf(1499));
        when(productDtoMock.getDetails()).thenReturn("Prepare-se para experienciar um novo nível de imersão televisiva com a TV LG UHD AU7700.");

        final Product product = representationBuilder.fromRepresentation(productDtoMock);

        assertNotNull(product);
        assertEquals(productDtoMock.getId(), product.getId());
        assertEquals(productDtoMock.getName(), product.getName());
        assertEquals(productDtoMock.getColor(), product.getColor());
        assertEquals(productDtoMock.getValue(), product.getValue());
        assertEquals(productDtoMock.getDetails(), product.getDetails());

        verify(productDtoMock, times(2)).getId();
        verify(productDtoMock, times(2)).getName();
        verify(productDtoMock, times(2)).getColor();
        verify(productDtoMock, times(2)).getValue();
        verify(productDtoMock, times(2)).getDetails();
    }
}