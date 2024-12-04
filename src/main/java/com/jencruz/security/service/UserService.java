package com.jencruz.security.service;

import com.jencruz.security.model.User;

public interface UserService {
    User getUser(Integer id);
    User getUser(String username);
    User saveUser(User user);
}
