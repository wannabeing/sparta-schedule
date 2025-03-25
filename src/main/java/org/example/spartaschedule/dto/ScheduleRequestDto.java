package org.example.spartaschedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    // 유효성 그룹 인터페이스 정의
    public interface OnCreate {}
    public interface OnUpdate {}
    public interface OnDelete {}

    @NotBlank(message = "할 일을 입력해주세요.", groups = {OnCreate.class, OnUpdate.class})
    private String todo;

    @NotBlank(message = "작성자를 입력해주세요.", groups = {OnCreate.class, OnUpdate.class})
    private String writer; // FIXME: 추후 작성자 ID로 변경

    @NotBlank(message = "비밀번호를 입력해주세요.", groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    private String password;
}
