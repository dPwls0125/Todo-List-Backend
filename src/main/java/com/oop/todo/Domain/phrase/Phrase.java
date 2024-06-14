//package com.oop.todo.Domain.phrase;
//
//import com.oop.todo.Domain.User.entity.UserEntity;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import java.time.LocalDateTime;
//
//
//@Entity
//@Data
//@NoArgsConstructor
//public class Phrase {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id ;
//
//    @CreatedDate
//    @Column(updatable = false)
//    private LocalDateTime createAt;
//
//    @LastModifiedDate
//    private LocalDateTime updateAt;
//
//    private String todayPhrase;
//
//    private Boolean isChecked;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity userEntity;
//
//}
