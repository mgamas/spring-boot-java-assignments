package org.adaschool.api.service.product;

import org.adaschool.api.repository.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductsServiceTest {

    private ProductsService service;

    @BeforeEach
    void setUp() {
        service = new ProductsServiceMap();
    }

    private static Product p(String name, String description, String category, double price) {
        Product x = new Product();
        x.setName(name);
        x.setDescription(description);
        x.setCategory(category);
        x.setPrice(price);
        return x;
    }

    @Test
    void save_assignsId_whenNull() {
        Product created = service.save(p("Laptop", "Ultrabook", "Computers", 1200.0));
        assertNotNull(created.getId());
        assertEquals("Laptop", created.getName());
    }

    @Test
    void createTwo_andBothExist() {
        String id1 = service.save(p("A", "desc A", "cat", 1.0)).getId();
        String id2 = service.save(p("B", "desc B", "cat", 2.0)).getId();

        assertTrue(service.findById(id1).isPresent());
        assertTrue(service.findById(id2).isPresent());
        assertNotEquals(id1, id2);
    }

    @Test
    void findById_found() {
        String id = service.save(p("Mouse", "optical", "Peripherals", 15.0)).getId();
        Optional<Product> got = service.findById(id);
        assertTrue(got.isPresent());
        assertEquals("Mouse", got.get().getName());
    }

    @Test
    void update_updatesWhenExists() {
        String id = service.save(p("Keyboard", "membrane", "Peripherals", 20.0)).getId();

        Product req = p("Keyboard Pro", "mechanical", "Peripherals", 45.0);
        Product updated = service.update(req, id);

        assertNotNull(updated);
        assertEquals(id, updated.getId());
        assertEquals("Keyboard Pro", updated.getName());
        assertEquals(45.0, updated.getPrice(), 0.0001);
    }

    @Test
    void update_returnsNullWhenNotExists() {
        Product updated = service.update(p("X", "Y", "Z", 1.0), "missing");
        assertNull(updated);
    }

    @Test
    void deleteById_removesItem() {
        String id = service.save(p("Cam", "4k", "Video", 99.0)).getId();
        assertTrue(service.findById(id).isPresent());

        service.deleteById(id);

        assertFalse(service.findById(id).isPresent());
    }
}
