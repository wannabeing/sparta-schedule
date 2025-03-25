package org.example.spartaschedule.service;


import org.example.spartaschedule.dto.ScheduleRequestDto;
import org.example.spartaschedule.dto.ScheduleResponseDto;
import org.example.spartaschedule.entity.Schedule;
import org.example.spartaschedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, PasswordEncoder passwordEncoder){
        this.scheduleRepository = scheduleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * [Service] 일정 생성 메서드
     * @param dto 일정 요청 객체
     * @return 일정 응답 객체
     */
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        // 암호화된 비밀번호 필드
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        // 사용자 입력값으로 새로운 일정 객체 생성
        Schedule schedule = new Schedule(dto.getTodo(), dto.getWriter(), encryptedPassword);

        return scheduleRepository.createSchedule(schedule);
    }

    /**
     * [Service] 전체 일정 조회 메서드
     * @return 일정 응답 객체 리스트
     */
    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return scheduleRepository.findAllSchedules();
    }

    /**
     * [Service] 단일 일정 조회 메서드
     * @param id 일정 id
     * @return 일정 응답 객체 또는 예외 처리
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        return scheduleRepository
                .findScheduleById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));
    }
}
