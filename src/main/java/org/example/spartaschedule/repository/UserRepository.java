package org.example.spartaschedule.repository;

import org.example.spartaschedule.entity.User;

import java.util.Optional;

public interface UserRepository {
    /**
     * [Repo] 유저를 조회하는 메서드
     * @param id 유저 id
     * @return 조회된 유저 객체가 담긴 Optional 객체
     */
    Optional<User> findUserById(Long id);
}
