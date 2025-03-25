package org.example.spartaschedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private String todo;
    private Long userId; // 외래키
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ 생성자
    public Schedule(String todo, Long userId, String password){
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }
}
