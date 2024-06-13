package com.oop.todo.Domain.User.controller;

import com.oop.todo.Domain.User.service.UserService;
import com.oop.todo.Domain.User.entity.dto.UserDTO;
import com.oop.todo.Domain.User.entity.UserEntity;
import com.oop.todo.ResponseDTO;
import com.oop.todo.global.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;
    @PostMapping("/signUp")
    public ResponseEntity<?>registerUser(@RequestBody UserDTO userDTO){
        try{
            UserEntity userEntity = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .email(userDTO.getEmail())
                    .build();

            UserEntity registeredUserEntity = userService.create(userEntity);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(registeredUserEntity.getUsername())
                    .email(registeredUserEntity.getEmail())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        }catch(Exception e){
            ResponseDTO responseDto = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }


    @PostMapping("/signIn")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){

        UserEntity userEntity = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());

        if(userEntity != null){
            final String token =  tokenProvider.create(userEntity);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .id(userEntity.getId())
                    .email(userEntity.getEmail())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.status(401).body(responseDTO);
        }
    }


}
