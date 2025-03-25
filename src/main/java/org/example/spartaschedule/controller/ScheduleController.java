package org.example.spartaschedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.spartaschedule.dto.ApiResponseDto;
import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.service.ScheduleService;
import org.example.spartaschedule.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor // final 필드를 가진 생성자를 자동으로 만들어줌
@RestController
@RequestMapping("/{userId}/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    // ✅ 공통 유저 유효성 검사 (@ModelAttribute 가 Controller 실행전에 먼저 실행)
    @ModelAttribute("validUserId")
    public Long validateUser(@PathVariable Long userId) {
        return userService.findUserById(userId).getId();
    }

    /**
     * [Controller] 일정 생성 메서드
     *
     * @param dto 사용자 일정 요청 객체
     * @return 생성된 일정 응답 객체
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @Validated(ScheduleRequestDto.OnCreate.class) @RequestBody ScheduleRequestDto dto,
            @ModelAttribute("validUserId") Long validUserId
    ){
        // 생성된 일정 응답 객체 반환
        return new ResponseEntity<>(scheduleService.createSchedule(validUserId, dto), HttpStatus.CREATED);
    }

    /**
     * [Controller] 모든 일정을 조회하는 메서드
     *
     * @return 일정 응답 객체 리스트
     */
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @ModelAttribute("validUserId") Long validUserId
    ){
        // 리스트<일정 응답 객체> 반환
        return new ResponseEntity<>(scheduleService.findAllSchedules(validUserId), HttpStatus.OK);
    }

    /**
     * [Controller] 일정을 수정하는 메서드
     *
     * @param dto 사용자가 수정하고 싶은 일정 요청 객체
     * @param validUserId 유효성 검증된 유저 id
     * @param scheduleId 일정 id
     * @return 수정된 일정 응답 객체
     */
    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @Validated(ScheduleRequestDto.OnUpdate.class) @RequestBody ScheduleRequestDto dto,
            @ModelAttribute("validUserId") Long validUserId,
            @PathVariable Long scheduleId
    ){
        return new ResponseEntity<>(scheduleService.updateSchedule(validUserId, scheduleId, dto), HttpStatus.OK);
    }

    /**
     * [Controller] 단일 일정을 조회하는 메서드
     *
     * @param scheduleId 일정 id
     * @param validUserId 유효성 검증된 유저 id
     * @return 특정 일정 객체 또는 못찾은 에러 메시지
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @ModelAttribute("validUserId") Long validUserId,
            @PathVariable Long scheduleId
    ){
        return new ResponseEntity<>(scheduleService.findScheduleById(validUserId, scheduleId), HttpStatus.OK);
    }

    /**
     *  [Controller] 일정을 삭제하는 메서드
     *
     * @param dto 사용자가 삭제하고 싶은 일정 요청 객체
     * @param scheduleId 일정 id
     * @param validUserId 유효성 검증된 유저 id
     * @return API 응답 객체
     */
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ApiResponseDto> deleteSchedule(
            @Validated(ScheduleRequestDto.OnDelete.class) @RequestBody ScheduleRequestDto dto,
            @ModelAttribute("validUserId") Long validUserId,
            @PathVariable Long scheduleId

    ){
        // 프론트에게 응답메시지 보내기 위해 204(NO_CONTENT) 대신 200(OK) 반환
        return new ResponseEntity<>(scheduleService.deleteSchedule(validUserId, scheduleId, dto.getPassword()), HttpStatus.OK);
    }
}
