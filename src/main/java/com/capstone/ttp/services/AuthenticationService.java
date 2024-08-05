package com.capstone.ttp.services;

import com.capstone.ttp.dtos.LoginUserDto;
import com.capstone.ttp.dtos.RegisterUserDto;
import com.capstone.ttp.entitiy.Role;
import com.capstone.ttp.entitiy.RoleEnum;
import com.capstone.ttp.entitiy.User;
import com.capstone.ttp.repositories.RoleRepository;
import com.capstone.ttp.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) throws CustomSignupException {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        if (userRepository.findByEmail(input.getEmail()).isPresent()) {
            throw new CustomSignupException("Account already exists");
        }

        // Validate email format
        if (!isValidEmail(input.getEmail())) {
            throw new CustomSignupException("Invalid email format.");
        }

        // Validate password strength
        if (!isValidPassword(input.getPassword())) {
            throw new CustomSignupException("Password must contain at least 8 characters, at least one letter and one number");
        }

        // Validate name
        if (input.getFullName() == null || input.getFullName().trim().isEmpty()) {
            throw new CustomSignupException("Full name cannot be empty.");
        }
        var user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid username or password.");
        }

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
    public static class CustomSignupException extends Exception {
        public CustomSignupException(String message) {
            super(message);
        }
    }

    // Utility methods to validate email and password
    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPassword(String password) {
        // Example password criteria: at least 8 characters, at least one letter and one number
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(passwordRegex);
    }

}