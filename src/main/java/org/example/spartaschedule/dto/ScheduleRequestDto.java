package org.example.spartaschedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank(message = "할 일을 입력해주세요.")
    private String todo;

    @NotBlank(message = "작성자를 입력해주세요.")
    private String writer; // FIXME: 추후 작성자 ID로 변경

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
