package com.oop.todo.Domain.User.entity;

import com.oop.todo.Domain.Todo.Entity.TodoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")}) // 제약 조건 추가
public class UserEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @Column(unique = true)
    private String oauthId;

    @Column(nullable=false)
    private String username;

//    @OneToMany(mappedBy = "userEntity")
//    private List<TodoEntity> todos;

    @Column(nullable=true, unique=true)
    private String email;

    @Column(nullable = true)
    private String password;

}

