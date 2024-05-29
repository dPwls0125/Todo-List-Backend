package com.oop.todo.Domain.Todo.service;


import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import com.oop.todo.Domain.Todo.Repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public Optional<TodoEntity> create(final TodoEntity entity){
        //Validations
        validate(entity);
        repository.save(entity);
        return repository.findById(entity.getId());
    }

    public List<TodoEntity>retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public void validate(final TodoEntity entity){
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId()==null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
