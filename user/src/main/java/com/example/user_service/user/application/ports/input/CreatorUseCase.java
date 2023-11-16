package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestCheckCreator;
import com.example.user_service.user.adapter.web.request.RequestDeleteCreator;
import com.example.user_service.user.adapter.web.request.RequestUpdateCreator;
import com.example.user_service.user.application.ports.output.dto.CheckCreatorDto;
import com.example.user_service.user.application.ports.output.dto.DeleteCreatorDto;
import com.example.user_service.user.application.ports.output.dto.AutoSearchCreatorsDto;
import com.example.user_service.user.application.ports.output.dto.UpdateCreatorDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface CreatorUseCase {

    UpdateCreatorDto registerCreator(UpdateCreatorQuery registerCreatorQuery);
    UpdateCreatorDto changeCreatorCategory(UpdateCreatorQuery updateCreatorQuery);
    DeleteCreatorDto deleteCreator(DeleteCreatorQuery deleteCreatorQuery);
    CheckCreatorDto checkCreator(CheckCreatorQuery checkCreatorQuery);
    List<AutoSearchCreatorsDto> autoSearchCreators(AutoSearchCreatorsQuery autoSearchCreatorsQuery); // todo: 미완성

    @Getter
    @Builder
    class UpdateCreatorQuery {

        private String uuid;
        private String categoryName;

        public static UpdateCreatorQuery toQuery(RequestUpdateCreator requestUpdateCreator) {

            return UpdateCreatorQuery.builder()
                    .uuid(requestUpdateCreator.getUuid())
                    .categoryName(requestUpdateCreator.getCategoryName())
                    .build();
        }
    }

    @Getter
    @Builder
    class DeleteCreatorQuery {

        private String uuid;

        public static DeleteCreatorQuery toQuery(RequestDeleteCreator requestDeleteCreator) {

            return DeleteCreatorQuery.builder()
                    .uuid(requestDeleteCreator.getUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class CheckCreatorQuery {

        private String uuid;

        public static CheckCreatorQuery toQuery(RequestCheckCreator requestCheckCreator) {

            return CheckCreatorQuery.builder()
                    .uuid(requestCheckCreator.getUuid())
                    .build();
        }
    }

    @Getter
    @Builder
    class AutoSearchCreatorsQuery {

        private String username; // 검색 유저네임

        public static AutoSearchCreatorsQuery toQuery(String username) {

            return AutoSearchCreatorsQuery.builder()
                    .username(username)
                    .build();
        }
    }
}
