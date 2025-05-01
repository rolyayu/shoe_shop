package com.github.shoe_shop.user.user;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.exceptions.EntityNotFoundException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(@NonNull final User userToCreate) {
        final Optional<User> foundedUser = userRepository.findByUsername(userToCreate.getUsername());
        if (foundedUser.isPresent()) {
            throw new EntityAlreadyExistsException("User already exists");
        }
        return userRepository.save(userToCreate);
    }

    public User findByUsername(@NonNull final String username) {
        final Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public User findById(@NonNull final Long id) {
        final Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
    }
}
