package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) { this.usersService = usersService; }

    // Acepta /v1/users y /v1/users/ (importante para el test del POST)
    @PostMapping({"", "/"})
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = new User(userDto);
        user.setId(null); // El test envía "id":"null"; lo ignoramos
        User savedUser = usersService.save(user);
        URI created = URI.create("/v1/users/" + savedUser.getId());
        return ResponseEntity.created(created).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        return usersService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return usersService.findById(id)
                .map(existing -> {
                    existing.setName(userDto.getName());
                    existing.setLastName(userDto.getLastName());
                    existing.setEmail(userDto.getEmail());
                    existing.setPhone(userDto.getPhone());
                    return ResponseEntity.ok(usersService.save(existing));
                })
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        return usersService.findById(id)
                .map(u -> {
                    usersService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseThrow(() -> new UserNotFoundException("user with ID: " + id + " not found"));
    }
}
