package com.example.demo.repositories;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@AllArgsConstructor
public class RepoInit {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    InitializingBean init () {
        return () -> {
            if (userRepository.findAll().isEmpty() || roleRepository.findAll().isEmpty()){
                Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                User user = new User("user", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder().encode("user"));

                User admin = new User("admin", true);
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder().encode("admin"));

                User test = new User("superuser", true);
                test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                test.setPassword(passwordEncoder().encode("superuser "));

                userRepository.save(user);
                userRepository.save(admin);
                userRepository.save(test);
            }

        };
    }
}
