package org.example.spartaschedule.service;

import org.example.spartaschedule.dto.UserResponseDto;
import org.example.spartaschedule.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    /**
     * [Service] 유저를 조회하는 메서드
     * @param id 유저 id
     * @return 유저 응답 객체 반환
     */
    @Override
    public UserResponseDto findUserById(Long id) {
        return userRepository
                .findUserById(id)
                .map(UserResponseDto::new)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "유효하지 않은 유저 id 입니다."));
    }
}
