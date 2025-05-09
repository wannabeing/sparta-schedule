package org.example.spartaschedule.service;


import org.example.spartaschedule.dto.ApiResponseDto;
import org.example.spartaschedule.dto.PageScheduleResponseDto;
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
     *
     * @param userId 유저 id
     * @param dto 사용자 요청으로 생성한 일정 요청 객체
     * @return 일정 응답 객체
     */
    @Override
    public ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto dto) {
        // 암호화된 비밀번호 필드
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());

        // 사용자 입력값으로 새로운 일정 객체 생성
        Schedule schedule = new Schedule(dto.getTodo(), userId, encryptedPassword);

        return scheduleRepository.createSchedule(schedule);
    }

    /**
     * [Service] 페이징된 일정 목록 조회 메서드
     *
     * @param currentPage 현재 페이지
     * @param pageSize 페이지 당 개수
     * @param userId 유저 id
     * @return 일정 응답 객체 리스트
     */
    @Override
    public PageScheduleResponseDto findPagedSchedules(Long userId, int currentPage, int pageSize) {
        // 1. 몇번째 행부터 가져올지 정하는 변수
        int offset = (currentPage - 1) * pageSize;

        // 2. offset, size 에 맞게 가져온 일정 목록
        List<Schedule> schedules = scheduleRepository.findPagedSchedules(userId, offset, pageSize);

        // 3. 총 일정 개수 확인
        Long totalSchedules = scheduleRepository.findTotalSchedules(userId);
        int totalPages = (int) Math.ceil((double) totalSchedules / pageSize);

        // 4. 일정 객체 리스트 -> 일정 응답 객체 리스트로 변환
        List<ScheduleResponseDto> scheduleResponseDtoList = schedules
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();

        // 5. 페이지 일정 응답 객체 반환
        return new PageScheduleResponseDto(currentPage, pageSize, totalSchedules, totalPages, scheduleResponseDtoList);
    }

    /**
     * [Service] 단일 일정 조회 메서드
     *
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @return 일정 응답 객체 또는 예외 처리
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long userId, Long scheduleId) {
        // 찾은 일정 객체 -> 일정 응답 객체로 변환하여 반환
        return scheduleRepository
                .findScheduleById(userId, scheduleId)
                .map(ScheduleResponseDto::new)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));
    }

    /**
     * [Service] 일정 수정 메서드
     *
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @param dto 사용자 요청으로 생성한 일정 요청 객체
     * @return 수정한 일정 응답 객체 또는 예외 처리
     */
    @Override
    public ScheduleResponseDto updateSchedule(Long userId, Long scheduleId, ScheduleRequestDto dto) {
        // 1. 존재하는 일정인지 확인
        Schedule existSchedule = scheduleRepository
                .findScheduleById(userId, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));

        // 2. 비밀번호 비교
        if (!passwordEncoder.matches(dto.getPassword(), existSchedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 3. 업데이트 성공적으로 수행했는지 확인
        int updated = scheduleRepository.updateSchedule(userId, scheduleId, dto);
        if(updated == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정에 실패했습니다.");
        }

        // 4. 수정된 일정 응답 객체 반환
        return this.findScheduleById(userId, scheduleId);
    }

    /**
     * [Service] 일정을 삭제하는 메서드
     *
     * @param scheduleId 일정 id
     * @param userId 유저 id
     * @param password 비밀번호
     * @return API 응답 객체
     */
    @Override
    public ApiResponseDto deleteSchedule(Long userId, Long scheduleId, String password) {
        // 1. 존재하는 일정인지 확인
        Schedule existSchedule = scheduleRepository
                .findScheduleById(userId, scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 Id 입니다."));

        // 2. 비밀번호 비교
        if (!passwordEncoder.matches(password, existSchedule.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 3. 삭제를 성공적으로 수행했는지 확인
        int deleted = scheduleRepository.deleteSchedule(userId, scheduleId);
        if(deleted == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제에 실패했습니다.");
        }

        // 3. 상태메시지 응답 객체 반환
        return new ApiResponseDto("success","성공적으로 삭제하였습니다.");
    }
}
