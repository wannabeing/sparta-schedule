package org.example.spartaschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageScheduleResponseDto {
    private int currentPage; // 현재 페이지 번호
    private int pageSize; // 한 페이지당 일정 개수
    private Long totalSchedule; // 전체 일정 개수
    private int totalPages; // 전체 페이지 수
    private List<ScheduleResponseDto> schedules; // 보여주는 일정 목록
}
