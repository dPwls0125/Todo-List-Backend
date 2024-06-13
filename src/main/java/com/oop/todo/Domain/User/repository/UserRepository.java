package com.oop.todo.Domain.User.repository;

import com.oop.todo.Domain.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByOauthId(String oauthId);
    UserEntity findByEmailAndPassword(String email, String password);
}
