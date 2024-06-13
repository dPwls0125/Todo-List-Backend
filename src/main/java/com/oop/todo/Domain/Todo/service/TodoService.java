package com.oop.todo.Domain.Todo.service;


import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import com.oop.todo.Domain.Todo.Repository.TodoRepository;
import com.oop.todo.Domain.Todo.dto.TodoDTO;
import com.oop.todo.Domain.User.entity.UserEntity;
import com.oop.todo.Domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public Optional<TodoEntity>create(final TodoEntity entity){
        //Validations
        validate(entity);
        todoRepository.save(entity);
        return todoRepository.findById(entity.getId());
    }

    public TodoEntity create(String userId, TodoDTO todoDTO) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
        TodoEntity todo = TodoEntity.builder()
                .userEntity(user)
                .title(todoDTO.getTitle())
                .done(todoDTO.isDone())
                .build();
        todoRepository.save(todo);
        return todo;

    }

    public List<TodoEntity>retrieve(final String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User not found")
        );
        return todoRepository.findByUserEntity(user);
    }

    public Optional<TodoEntity>update(final TodoEntity entity) {
        //Validations
        validate(entity);
        if (todoRepository.existsById(entity.getId())){
            todoRepository.save(entity);
        }
        else
            throw new RuntimeException("Unknown id");

        return todoRepository.findById(entity.getId());
    }

    public Optional<TodoEntity>updateTodo(final TodoEntity entity) {
        //validations
        validate(entity);
        System.out.println(entity.toString());
        //테이블에서 id에 해당하는 데이터셋을 가져온다.
        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        //original에 담겨진 내용을 todo에 할당하고 title, done 값을 변경한다.
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoRepository.save(todo);
        });
        //위의 람다식과 동일한 표현
        return todoRepository.findById(entity.getId());
    }

    public String delete(final String id){
        if(todoRepository.existsById(id))
            todoRepository.deleteById(id);
        else
            throw new RuntimeException("id does not exist");

        return "Deleted:";
    }

    public void validate(final TodoEntity entity){
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserEntity() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
