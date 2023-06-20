package com.example.demo.repositories;

import com.example.demo.models.Profile;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository <Profile, Long> {
    Profile findByUser(User user);
}
