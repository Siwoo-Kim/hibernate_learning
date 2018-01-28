package com.hibernate.learning.service;

import com.hibernate.learning.domain.User;

import java.util.Optional;

public interface UserService {

    void join(User user);
    Optional<User> user(String email);

}
