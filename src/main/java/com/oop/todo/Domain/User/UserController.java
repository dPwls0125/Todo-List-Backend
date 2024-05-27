package com.oop.todo.Domain.User;

import com.oop.todo.ResponseDTO;
import com.oop.todo.Token.TokenProvider;
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
    private TokenProvider tokenProvider;
    @PostMapping("/signUp")
    public ResponseEntity<?>registerUser(@RequestBody UserDTO userDTO){
        try{
            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .email(userDTO.getEmail())
                    .build();

            UserEntity registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .username(registeredUser.getUsername())
                    .email(registeredUser.getEmail())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);

        }catch(Exception e){
            ResponseDTO responseDto = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());
        if(user != null){
            final String token =  tokenProvider.create(user);
            final UserDTO responseUserDTO = UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
