package com.oop.todo.Domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByEmailAndPassword(String email, String password);
}
