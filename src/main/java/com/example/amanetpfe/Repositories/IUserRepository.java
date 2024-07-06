package com.example.amanetpfe.Repositories;

import com.example.amanetpfe.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    User findUserByEmail (String email) ;

    Boolean existsByEmail(String email);

}
