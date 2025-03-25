package org.example.spartaschedule.repository;

import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    /**
     * [Repo] 일정 생성 메서드
     * @param schedule 생성된 일정 객체
     * @return 생성된 일정 응답 객체
     */
    ScheduleResponseDto createSchedule(Schedule schedule);

    /**
     * [Repo] 전체 일정 조회 메서드
     * @return 일정 응답 객체 리스트
     */
    List<ScheduleResponseDto> findAllSchedules();

    Optional<ScheduleResponseDto> findScheduleById(Long id);
}
