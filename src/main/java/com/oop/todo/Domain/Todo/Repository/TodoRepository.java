package com.oop.todo.Domain.Todo.Repository;

import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import com.oop.todo.Domain.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserEntity(UserEntity userEntity);
}
