package org.example.spartaschedule.service;


import org.example.spartaschedule.dto.ApiResponseDto;
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
     * @param dto 사용자 요청으로 생성한 일정 요청 객체
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
        // 일정 객체 리스트 -> 일정 응답 객체 리스트로 변환하여 반환
        return scheduleRepository.findAllSchedules()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    /**
     * [Service] 단일 일정 조회 메서드
     * @param id 일정 id
     * @return 일정 응답 객체 또는 예외 처리
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        // 찾은 일정 객체 -> 일정 응답 객체로 변환하여 반환
        return scheduleRepository
                .findScheduleById(id)
                .map(ScheduleResponseDto::new)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));
    }

    /**
     * [Service] 일정 수정 메서드
     * @param id 일정 id
     * @param dto 사용자 요청으로 생성한 일정 요청 객체
     * @return 수정한 일정 응답 객체 또는 예외 처리
     */
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        // 1. 존재하는 일정인지 확인
        Schedule existSchedule = scheduleRepository
                .findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));

        // 2. 비밀번호 비교
        if (!passwordEncoder.matches(dto.getPassword(), existSchedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 3. 업데이트 성공적으로 수행했는지 확인
        int updated = scheduleRepository.updateSchedule(id, dto);
        if(updated == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정에 실패했습니다.");
        }

        // 4. 수정된 일정 응답 객체 반환
        return this.findScheduleById(id);
    }

    /**
     * [Service] 일정을 삭제하는 메서드
     * @param id 삭제하고자 하는 일정 id
     * @param password 비밀번호
     * @return API 응답 객체
     */
    @Override
    public ApiResponseDto deleteSchedule(Long id, String password) {
        // 1. 존재하는 일정인지 확인
        Schedule existSchedule = scheduleRepository
                .findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));

        // 2. 비밀번호 비교
        if (!passwordEncoder.matches(password, existSchedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 3. 삭제를 성공적으로 수행했는지 확인
        int deleted = scheduleRepository.deleteSchedule(id);
        if(deleted == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제에 실패했습니다.");
        }

        // 3. 상태메시지 응답 객체 반환
        return new ApiResponseDto("success","성공적으로 삭제하였습니다.");
    }
}
