package org.example.spartaschedule.controller;

import org.example.spartaschedule.dto.ApiResponseDto;
import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
     * @param dto 사용자 일정 요청 객체
     * @return 생성된 일정 응답 객체
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Validated(ScheduleRequestDto.OnCreate.class) @RequestBody ScheduleRequestDto dto
    ){
        // 생성된 일정 응답 객체 반환
        return new ResponseEntity<>(scheduleService.createSchedule(dto), HttpStatus.CREATED);
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
     * [Controller] 단일 일정을 조회하는 메서드
     * @param id 일정 ID
     * @return 특정 일정 객체 또는 못찾은 에러 메시지
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ){
      return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * [Controller] 일정을 수정하는 메서드
     * @param dto 사용자가 수정하고 싶은 일정 요청 객체
     * @param id 수정하고자 하는 일정 id
     * @return 수정된 일정 응답 객체
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @Validated(ScheduleRequestDto.OnUpdate.class) @RequestBody ScheduleRequestDto dto,
            @PathVariable Long id
    ){
        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto), HttpStatus.OK);
    }

    /**
     *  /**
     *  [Controller] 일정을 삭제하는 메서드
     * @param dto 사용자가 삭제하고 싶은 일정 요청 객체
     * @param id 삭제하고자 하는 일정 id
     * @return API 응답 객체
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto> deleteSchedule(
            @Validated(ScheduleRequestDto.OnDelete.class) @RequestBody ScheduleRequestDto dto,
            @PathVariable Long id
    ){
        return new ResponseEntity<>(scheduleService.deleteSchedule(id, dto.getPassword()), HttpStatus.NO_CONTENT);
    }
}
