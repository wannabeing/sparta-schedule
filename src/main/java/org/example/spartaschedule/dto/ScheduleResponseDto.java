package org.example.spartaschedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.spartaschedule.entity.Schedule;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ScheduleResponseDto {
    private Long id;
    private String todo;
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // ✅ 생성자
    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.todo = schedule.getTodo();
        this.userId = schedule.getUserId();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
