package com.oop.todo.Domain.Todo.controller;


import com.oop.todo.Domain.Todo.dto.ResponseDTO;
import com.oop.todo.Domain.Todo.dto.TodoDTO;
import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import com.oop.todo.Domain.Todo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<?>createTodo(@RequestBody TodoDTO dto, @AuthenticationPrincipal String userId) {
        try {
            TodoEntity entity = todoService.create(userId,dto);
            log.info("Log:servce.create ok!");

            TodoDTO dtos = TodoDTO.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .done(entity.isDone())
                    .build();

            // entities를 dtos로 스트림 변환한다.
            log.info("Log:entities => dtos ok!");
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(List.of(dtos)).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?>retrieveTodoList(HttpServletRequest request,@AuthenticationPrincipal String userId) {

        List<TodoEntity> entities = todoService.retrieve(userId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // HTTP Status 200 상태로 response를 전송한다.
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<TodoDTO>> retriveTodoList(
            @AuthenticationPrincipal String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
            ){
        Page<TodoDTO> todoPages = todoService.retrieve(userId, page, size);
        return ResponseEntity.ok().body(todoPages);

    }


    @GetMapping("/update")
    public ResponseEntity<?>update(@RequestBody TodoDTO dto){
        try {
            //dto를 이용해 테이블에 저장하기 위한 entity를 생성한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            //service.create를 통해 repository에 entity를 저장한다.
            //이 때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = todoService.update(entity);

            //entities를 dtos로 스트림 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //Response DTO를 생선한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //HTTP Status 200 상태로 response를 전송한다.
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        try {
            //dto를 이용해 테이블에 저장하기 위한 entity를 생성한다.
            TodoEntity entity = TodoDTO.toEntity(dto);


            //service.create를 통해 repository에 entity를 저장한다.
            //이 때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = todoService.updateTodo(entity);

            //entities를 dtos로 스트림 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //Response DTO를 생성한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //HTTP Status 200상태로 response를 전송한다.
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody TodoDTO dto){
        try {
            List<String> message = new ArrayList<>(); //import java.util.ArrayList 추가
            String msg = todoService.delete(dto.getId());
            message.add(msg);
            //Response DTO를 생성한다.
            ResponseDTO<String> response = ResponseDTO.<String>builder().data(message).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
