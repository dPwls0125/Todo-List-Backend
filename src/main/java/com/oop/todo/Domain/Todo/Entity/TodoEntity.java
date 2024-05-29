package com.oop.todo.Domain.Todo.Entity;


import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

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

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "done")
    private boolean done;
}
