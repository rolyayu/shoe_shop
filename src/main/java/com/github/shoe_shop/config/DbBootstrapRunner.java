package com.github.shoe_shop.config;

import com.github.shoe_shop.user.user.User;
import com.github.shoe_shop.user.user.UserRepository;
import com.github.shoe_shop.user.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.create-users-on-bootstrap", havingValue = "true", matchIfMissing = false)
public class DbBootstrapRunner implements CommandLineRunner {
    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        final User admin = new User();
        admin.setUsername("administrator");
        admin.setEncodedPassword(encoder.encode("administrator"));
        admin.setRole(UserRole.ADMINISTRATOR);
        userRepository.save(admin);

        final User headOfBranch = new User();
        headOfBranch.setUsername("headofbranch");
        headOfBranch.setEncodedPassword(encoder.encode("headofbranch"));
        headOfBranch.setRole(UserRole.HEAD_OF_BRANCH);
        userRepository.save(headOfBranch);
    }
}
