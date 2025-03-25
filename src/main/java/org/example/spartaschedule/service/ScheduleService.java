package org.example.spartaschedule.service;

import org.example.spartaschedule.dto.ApiResponseDto;
import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    /**
     * [Service] 일정 생성 메서드
     * @param dto 일정 요청 객체
     * @return 일정 응답 객체
     */
    ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto dto);

    /**
     * [Service] 전체 일정 조회 메서드
     * @return 일정 응답 객체 리스트
     */
    List<ScheduleResponseDto> findAllSchedules(Long userId);

    /**
     * [Service] 단일 일정 조회 메서드
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @return 일정 응답 객체
     */
    ScheduleResponseDto findScheduleById(Long userId, Long scheduleId);

    /**
     * [Service] 일정 수정하는 메서드
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @param dto 사용자 요청으로 수정한 일정 요청 객체
     * @return 수정된 일정 응답 객체
     */
    ScheduleResponseDto updateSchedule(Long userId, Long scheduleId, ScheduleRequestDto dto);

    /**
     * [Service] 일정을 삭제하는 메서드
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @return 삭제 메시지를 반환
     */
    ApiResponseDto deleteSchedule(Long userId, Long scheduleId, String password);
}