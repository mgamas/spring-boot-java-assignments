package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServiceMap implements UsersService {

    private final Map<String, User> store = new HashMap<>();

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank() || "null".equalsIgnoreCase(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }
}
