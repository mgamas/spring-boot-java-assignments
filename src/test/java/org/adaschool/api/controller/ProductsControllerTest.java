package org.adaschool.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adaschool.api.repository.product.Product;
import org.adaschool.api.service.product.ProductsService;
import org.adaschool.api.controller.product.ProductsController; // <<=== IMPORT CORRECTO
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

    private static final String BASE_URL = "/products";

    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ProductsController controller; // <<=== usa el tipo del sub-paquete

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(controller).build();
    }

    // Helper: construir Product con ctor vacío + setters
    private static Product product(String id, String name, String description, String category, double price) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setDescription(description);
        p.setCategory(category);
        p.setPrice(price); // si es BigDecimal, usa: p.setPrice(new BigDecimal("15.488"));
        return p;
    }

    @Test
    void testFindByIdExistingProduct() throws Exception {
        Product prod = product("1", "Whole Milk", "Whole Milk 200ml", "Dairy", 15.488);
        when(productsService.findById("1")).thenReturn(Optional.of(prod));

        mockMvc.perform(get(BASE_URL + "/{id}", "1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("Whole Milk")))
                .andExpect(jsonPath("$.description", is("Whole Milk 200ml")))
                .andExpect(jsonPath("$.category", is("Dairy")))
                .andExpect(jsonPath("$.price", is(15.488)));

        verify(productsService, times(1)).findById("1");
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(productsService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/{id}", "999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productsService, times(1)).findById("999");
    }
}
