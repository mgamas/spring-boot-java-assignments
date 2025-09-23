package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.adaschool.api.service.UsersService;      // <- tus clases reales (sin .user)
import org.adaschool.api.service.UsersServiceMap;  // <- idem
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceTest {

    private UsersService service;

    @BeforeEach
    void setUp() {
        service = new UsersServiceMap(); // implementación en memoria
    }

    private static User u(String id, String name, String last, String email, String phone) {
        User x = new User();
        x.setId(id);
        x.setName(name);
        x.setLastName(last);
        x.setEmail(email);
        x.setPhone(phone);
        return x;
    }

    // 1) save asigna id si viene null
    @Test
    void save_assignsId_whenNull() {
        User created = service.save(u(null, "Ada", "Lovelace", "ada@mail.com", "111"));
        assertNotNull(created.getId());
        assertEquals("Ada", created.getName());
    }

    // 2) crear dos usuarios y verificar que ambos existen (sin usar findAll)
    @Test
    void createTwo_andBothExist() {
        String id1 = service.save(u(null, "Ada", "L", "a@mail.com", "1")).getId();
        String id2 = service.save(u(null, "Grace", "H", "g@mail.com", "2")).getId();

        assertTrue(service.findById(id1).isPresent());
        assertTrue(service.findById(id2).isPresent());
        assertNotEquals(id1, id2);
    }

    // 3) findById encuentra cuando existe
    @Test
    void findById_found() {
        String id = service.save(u(null, "Ada", "L", "a@mail.com", "1")).getId();
        Optional<User> got = service.findById(id);
        assertTrue(got.isPresent());
        assertEquals("Ada", got.get().getName());
    }

    // 4) "update" por upsert: guardar con el mismo id reemplaza datos
    @Test
    void upsert_withSameId_updatesData() {
        String id = service.save(u(null, "Ada", "L", "a@mail.com", "1")).getId();

        User req = u(id, "Ada Updated", "L.", "new@mail.com", "999"); // mismo id
        User updated = service.save(req); // upsert por save()

        assertEquals(id, updated.getId());
        assertEquals("Ada Updated", updated.getName());
        assertEquals("999", updated.getPhone());
        assertEquals("new@mail.com", updated.getEmail());
    }

    // 5) findById retorna vacío cuando NO existe
    @Test
    void findById_notFound() {
        assertFalse(service.findById("no-existe").isPresent());
    }

    // 6) deleteById elimina el elemento
    @Test
    void deleteById_removesItem() {
        String id = service.save(u(null, "Ada", "L", "a@mail.com", "1")).getId();
        assertTrue(service.findById(id).isPresent());

        service.deleteById(id);

        assertFalse(service.findById(id).isPresent());
    }
}
