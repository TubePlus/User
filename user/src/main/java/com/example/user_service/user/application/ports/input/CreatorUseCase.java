package com.example.user_service.user.application.ports.input;

import com.example.user_service.user.application.ports.output.dto.DeleteCreatorDto;
import com.example.user_service.user.application.ports.output.dto.UpdateCreatorDto;
import lombok.Builder;
import lombok.Getter;

public interface CreatorUseCase {

    UpdateCreatorDto registerCreator(UpdateCreatorQuery registerCreatorQuery);
    UpdateCreatorDto changeCreatorCategory(UpdateCreatorQuery updateCreatorQuery);
    DeleteCreatorDto deleteCreator(DeleteCreatorQuery deleteCreatorQuery);

    @Getter
    @Builder
    class UpdateCreatorQuery {

        private String uuid;
        private String categoryName;

        public static UpdateCreatorQuery toQuery(String uuid, String categoryName) {

            return UpdateCreatorQuery.builder()
                    .uuid(uuid)
                    .categoryName(categoryName)
                    .build();
        }
    }

    @Getter
    @Builder
    class DeleteCreatorQuery {

        private String uuid;

        public static DeleteCreatorQuery toQuery(String uuid) {

            return DeleteCreatorQuery.builder()
                    .uuid(uuid)
                    .build();
        }
    }
}
