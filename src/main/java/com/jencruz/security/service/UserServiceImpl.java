package com.jencruz.security.service;

import com.jencruz.security.exception.EntityNotFoundException;
import com.jencruz.security.model.User;
import com.jencruz.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return unwrapUser(user, 404);
    }

    @Override
    public User saveUser(User user) {
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return userRepository.save(user);
    }

    static User unwrapUser(Optional<User> user, Integer id) {
        if (user.isPresent()) return user.get();
        else throw new EntityNotFoundException(id.longValue(), User.class);
    }
}
