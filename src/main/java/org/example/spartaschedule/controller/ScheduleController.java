package org.example.spartaschedule.controller;

import jakarta.validation.Valid;
import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    /**
     * [Controller] 일정 생성 메서드
     * @param requestDto 사용자 일정 요청 객체
     * @return 생성된 일정 응답 객체 또는 유효성 검사 에러 메시지
     */
    @PostMapping
    public ResponseEntity<?> createSchedule(
            @Valid @RequestBody ScheduleRequestDto requestDto
    ){
        // 생성된 일정 응답 객체 반환
        return new ResponseEntity<>(scheduleService.createSchedule(requestDto), HttpStatus.CREATED);
    }

    /**
     * [Controller] 모든 일정을 조회하는 메서드
     * @return 일정 응답 객체 리스트
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(){
        // 리스트<일정 응답 객체> 반환
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    /**
     * [Controller]
     * @param id 일정 ID
     * @return 특정 일정 객체 또는 못찾은 에러 메시지
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ){
      return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }
}
