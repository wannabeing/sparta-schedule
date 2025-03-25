package org.example.spartaschedule.service;

import org.example.spartaschedule.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    /**
     * [Service] 유저를 조회하는 메서드
     * @param id 유저 id
     * @return 유저 응답 객체 반환
     */
    UserResponseDto findUserById(Long id);

    List<UserResponseDto> findAllUsers();
}
