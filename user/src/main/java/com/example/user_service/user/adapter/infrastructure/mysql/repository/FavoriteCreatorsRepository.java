package com.example.user_service.user.adapter.infrastructure.mysql.repository;

import com.example.user_service.user.adapter.infrastructure.mysql.entity.FavoriteCreatorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteCreatorsRepository extends JpaRepository<FavoriteCreatorsEntity, Long> {

    Boolean existsByCreatorUuidAndUserUuid(String creatorUuid, String userUuid);
    FavoriteCreatorsEntity findByCreatorUuidAndUserUuid(String creatorUuid, String userUuid);
}
