package com.example.user_service.user.adapter.infrastructure.mysql.persistence;

import com.example.user_service.global.error.ErrorCode;
import com.example.user_service.global.error.handler.BusinessException;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.FavoriteCreatorsEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.QFavoriteCreatorsEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.entity.UserEntity;
import com.example.user_service.user.adapter.infrastructure.mysql.repository.FavoriteCreatorsRepository;
import com.example.user_service.user.adapter.infrastructure.mysql.repository.UserRepository;
import com.example.user_service.user.application.ports.output.FavoriteCreatorsPort;
import com.example.user_service.user.domain.FavoriteCreator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteCreatorsAdaptor implements FavoriteCreatorsPort {

    private final FavoriteCreatorsRepository favoriteCreatorsRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    // 크리에이터 즐겨찾기 추가
    @Transactional
    @Override
    public FavoriteCreator addFavoriteCreator(FavoriteCreator favoriteCreator) {

        FavoriteCreatorsEntity entity =
                favoriteCreatorsRepository.save(FavoriteCreatorsEntity.favDomainToEntity(favoriteCreator));

        return FavoriteCreator.favEntityToDomain(entity);
    }

    // 즐겨찾기한 크리에이터 조회
    @Override
    public List<FavoriteCreator> readFavoriteCreators(FavoriteCreator favoriteCreator) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QFavoriteCreatorsEntity c = QFavoriteCreatorsEntity.favoriteCreatorsEntity;

        // 1. userUuid로 즐겨찾기한 크리에이터 uuid 조회
        List<String> creatorUuids = queryFactory.select(c.creatorUuid)
                .from(c)
                .where(c.userUuid.eq(favoriteCreator.getUserUuid()))
                .fetch();

        ArrayList<FavoriteCreator> favoriteCreatorList = new ArrayList<>();

        for(String creatorUuid: creatorUuids) {

            // 2. 크리에이터 uuid로 userRepository에서 크리에이터 정보 불러오기
            Optional<UserEntity> creatorEntity = userRepository.findByUuid(creatorUuid);

            // 3. 크리에이터 정보 FavoriteCreator 도메인 객체에 담아서 List에 add해주기
            creatorEntity.ifPresent(entity -> favoriteCreatorList.add(
                    FavoriteCreator.builder()
                            .userUuid(favoriteCreator.getUserUuid())
                            .creatorUuid(creatorUuid)
                            .creatorName(entity.getUsername())
                            .creatorProfileImage(entity.getProfileImage())
                            .creatorBio(entity.getBio())
                            .creatorCategory(entity.getCategory())
                            .build()
            ));
        }

        return favoriteCreatorList;
    }

    @Transactional
    @Override
    public FavoriteCreator deleteFavoriteCreator(FavoriteCreator favoriteCreator) {

        FavoriteCreatorsEntity targetEntity = favoriteCreatorsRepository.findByCreatorUuidAndUserUuid(
                favoriteCreator.getCreatorUuid(),
                favoriteCreator.getUserUuid());

        favoriteCreatorsRepository.delete(targetEntity);
        return FavoriteCreator.favEntityToDomain(targetEntity);
    }

    // 크리에이터 즐겨찾기 중복 확인
    @Override
    public void isFavoriteCreatorDuplicate(String creatorUuid, String uuid) {

        if (favoriteCreatorsRepository.existsByCreatorUuidAndUserUuid(creatorUuid, uuid)) {
            throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

}
