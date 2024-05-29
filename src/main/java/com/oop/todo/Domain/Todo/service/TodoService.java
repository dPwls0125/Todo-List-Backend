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

    public Optional<TodoEntity>create(final TodoEntity entity){
        //Validations
        validate(entity);
        repository.save(entity);
        return repository.findById(entity.getId());
    }

    public List<TodoEntity>retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<TodoEntity>update(final TodoEntity entity) {
        //Validations
        validate(entity);
        if (repository.existsById(entity.getId())){
            repository.save(entity);
        }
        else
            throw new RuntimeException("Unknown id");

        return repository.findById(entity.getId());
    }

    public Optional<TodoEntity>updateTodo(final TodoEntity entity) {
        //validations
        validate(entity);
        System.out.println(entity.toString());
        //테이블에서 id에 해당하는 데이터셋을 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        //original에 담겨진 내용을 todo에 할당하고 title, done 값을 변경한다.
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });
        //위의 람다식과 동일한 표현
        return repository.findById(entity.getId());
    }

    public String delete(final String id){
        if(repository.existsById(id))
            repository.deleteById(id);
        else
            throw new RuntimeException("id does not exist");

        return "Deleted:";
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
