package org.adaschool.api.service;

import org.adaschool.api.repository.user.User;

import java.util.Optional;

public interface UsersService {
    Optional<User> findById(String id);
    User save(User user);
    void deleteById(String id);
}
