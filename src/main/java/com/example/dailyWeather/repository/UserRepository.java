package com.example.dailyWeather.repository;

import com.example.dailyWeather.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsernameAndDeletedFalse(String username);
    boolean existsByUsernameAndDeletedFalse(String email);
    Optional<User> findByIdAndDeletedFalse(UUID id);
    Page<User> findAllByDeletedFalse(Pageable pageable);

}
