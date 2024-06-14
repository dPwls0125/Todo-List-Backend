package com.oop.todo.Domain.phrase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phrase")
public class phraseController {

    private final phraseService phraseService;

    @GetMapping("/get")
    public ResponseEntity<?> getPhrase(@AuthenticationPrincipal String userId) {
        String phrase =  phraseService.getPhrase(userId);
        return ResponseEntity.ok().body(phrase);
    }



}
