package org.example.spartaschedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = false) // 존재하지 않는 필드가 들어오면 무시 false
@Getter
public class ScheduleRequestDto {
    // 유효성 그룹 인터페이스 정의
    public interface OnCreate {}
    public interface OnUpdate {}
    public interface OnDelete {}

    @NotBlank(message = "할 일을 입력해주세요.", groups = {OnCreate.class, OnUpdate.class})
    @Size(max = 200, message = "할 일은 200자 이내로 입력해주세요.", groups = {OnCreate.class, OnUpdate.class})
    private String todo;

    @NotBlank(message = "비밀번호를 입력해주세요.", groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    @Size(max = 20, message = "비밀번호는 20자 이내로 입력해주세요.", groups = {OnCreate.class, OnUpdate.class})
    private String password;
}
