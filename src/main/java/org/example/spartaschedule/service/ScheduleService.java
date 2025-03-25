package org.example.spartaschedule.service;

import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    /**
     * [Service] 일정 생성 메서드
     * @param dto 일정 요청 객체
     * @return 일정 응답 객체
     */
    ScheduleResponseDto createSchedule(ScheduleRequestDto dto);

    /**
     * [Service] 전체 일정 조회 메서드
     * @return 일정 응답 객체 리스트
     */
    List<ScheduleResponseDto> findAllSchedules();

    /**
     * [Service] 단일 일정 조회 메서드
     * @param id 일정 id
     * @return 일정 응답 객체
     */
    ScheduleResponseDto findScheduleById(Long id);

    /**
     * [Service] 일정 수정하는 메서드
     * @param id 일정 id
     * @param dto 사용자 요청으로 수정한 일정 요청 객체
     * @return 수정된 일정 응답 객체
     */
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto);
}