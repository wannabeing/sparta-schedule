package org.example.spartaschedule.dto;

/**
 * [API 응답 DTO]
 * record: @AllArgsConstructor + @Getter + a
 *
 * @param status 상태
 * @param message 메시지
 *
 */
public record ApiResponseDto(String status, String message) {}

