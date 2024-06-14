package com.oop.todo.Domain.User.controller;


import com.oop.todo.Domain.User.entity.dto.EmailDto;
import com.oop.todo.Domain.User.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/certification/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/request/certNum")
    public ResponseEntity requestCertNum(@RequestParam("email") String email){
        log.info("MailController.requestCertNum is running");
        mailService.sendEmail(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate")
    public ResponseEntity validateEmail(@RequestBody EmailDto dto ){
        log.debug("MailController.validationEmail is running");
        try{
            mailService.validateEmail(dto);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
