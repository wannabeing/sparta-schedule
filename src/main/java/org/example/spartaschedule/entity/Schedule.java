package org.example.spartaschedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private String todo;
    private String writer; // FIXME: 추후 작성자 ID로 변경
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ 생성자
    public Schedule(String todo, String writer, String password){
        this.todo = todo;
        this.writer = writer;
        this.password = password;
    }
}
