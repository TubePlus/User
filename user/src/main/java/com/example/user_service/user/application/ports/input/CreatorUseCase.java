package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.adapter.web.request.RequestCheckCreator;
import com.example.user_service.user.adapter.web.request.RequestDeleteCreator;
import com.example.user_service.user.adapter.web.request.RequestUpdateCreator;
import com.example.user_service.user.application.ports.output.dto.CheckCreatorDto;
import com.example.user_service.user.application.ports.output.dto.DeleteCreatorDto;
import com.example.user_service.user.application.ports.output.dto.SearchCreatorsDto;
import com.example.user_service.user.application.ports.output.dto.UpdateCreatorDto;
import lombok.Builder;
import lombok.Getter;

public interface CreatorUseCase {

    UpdateCreatorDto registerCreator(UpdateCreatorQuery registerCreatorQuery);
    UpdateCreatorDto changeCreatorCategory(UpdateCreatorQuery updateCreatorQuery);
    DeleteCreatorDto deleteCreator(DeleteCreatorQuery deleteCreatorQuery);
    CheckCreatorDto checkCreator(CheckCreatorQuery checkCreatorQuery);
    SearchCreatorsDto autoSearchCreators(AutoSearchCreatorsQuery autoSearchCreatorsQuery); // todo: 미완성

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

    // todo: 미완성입니다.
    @Getter
    @Builder
    class AutoSearchCreatorsQuery {

        private String q; // 검색 키워드

        public static AutoSearchCreatorsQuery toQuery(String q) {

            return AutoSearchCreatorsQuery.builder()
                    .q(q)
                    .build();
        }
    }
}
