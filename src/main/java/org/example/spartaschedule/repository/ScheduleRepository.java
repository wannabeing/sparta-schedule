package org.example.spartaschedule.repository;

import org.example.spartaschedule.dto.ScheduleRequestDto;
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
     * @return 일정 객체 리스트
     */
    List<Schedule> findAllSchedules();

    /**
     * [Repo] 단일 일정 조회 메서드
     * @param id 일정 id
     * @return 일정 객체
     */
    Optional<Schedule> findScheduleById(Long id);

    /**
     * [Repo] 일정 수정 메서드
     * @param id 일정 id
     * @param dto 사용자 요청으로 수정한 일정 요청 객체
     * @return 수정된 객체 수 반환
     */
    int updateSchedule(Long id, ScheduleRequestDto dto);

    /**
     * [Repo] 일정 삭제 메서드
     * @param id 삭제하고자 하는 일정 id
     * @return 삭제된 객체 수 반환
     */
    int deleteSchedule(Long id);
}
