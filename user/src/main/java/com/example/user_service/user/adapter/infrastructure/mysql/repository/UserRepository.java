package com.example.user_service.user.adapter.infrastructure.mysql.repository;

import com.example.user_service.user.adapter.infrastructure.mysql.entity.UserEntity;
import com.example.user_service.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 로그인시 유저 가입 여부 확인
    Optional<UserEntity> findByEmail(String email);

    // 회원가입
    UserEntity save(UserEntity userEntity);

    // 유저네임 중복 체크
    Boolean existsByUsername(String username);

    // 유저 회원정보 조회
    Optional<UserEntity> findByUuid(String uuid);
}
