package com.oop.todo.Domain.Todo.Entity;


import com.oop.todo.Domain.User.entity.UserEntity;
import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name ="Todo")
public class TodoEntity {
    @Id
    @GeneratedValue(generator="system-uuid") //자동으로 id 생성
    @GenericGenerator(name="system-uuid",strategy="uuid")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "title")
    private String title;

    @Column(name = "done")
    private boolean done;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "priority")
    private Priority priority;


}
