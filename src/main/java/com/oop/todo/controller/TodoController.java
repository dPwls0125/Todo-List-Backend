package com.oop.todo.controller;


import com.oop.todo.dto.ResponseDTO;
import com.oop.todo.dto.TodoDTO;
import com.oop.todo.model.TodoEntity;
import com.oop.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private TodoService service;

    @PostMapping
    public ResponseEntity<?>createTodo(@RequestBody TodoDTO dto) {
        try {
//            /* POST localhost:8080/todo
//             * {
//             * "title" : "My first todo",
//             * "done" : false
//             * }
//             */
            log.info("Log:createTodo entrance");

            // dto 를 이용해 테이블에 저장하기 위한 entity를 생성한다.
            TodoEntity entity = TodoDTO.toEntity(dto);
            log.info("Log:dto => entity ok!");

            // entity userId를 임시로 지정한다.
            entity.setUserId("temporary-userid");

            // service.create를 통해 repository에 entity를 저장한다
            // 이때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = service.create(entity);
            log.info("Log:servce.create ok!");

            // entities를 dtos로 스트림 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            log.info("Log:entities => dtos ok!");

            // Response DTO를 생성한다.
            /* {
             *
             */
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            log.info("Log:responsedto ok!");

            // HTTP Status 200 상태로 response를 전송한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?>retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // HTTP Status 200 상태로 response를 전송한다.
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/update")
    public ResponseEntity<?>update(@RequestBody TodoDTO dto){
        try {
            //dto를 이용해 테이블에 저장하기 위한 entity를 생성한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            //entity userId를 임시로 지정한다.
            entity.setUserId("Temporary-user");

            //service.create를 통해 repository에 entity를 저장한다.
            //이 때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = service.update(entity);

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

            //entity userId를 임시로 지정한다.
            entity.setUserId("Temporary-user");

            //service.create를 통해 repository에 entity를 저장한다.
            //이 때 넘어오는 값이 없을 수도 있으므로 List가 아닌 Optional로 한다.
            Optional<TodoEntity> entities = service.updateTodo(entity);

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
            String msg = service.delete(dto.getId());
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
