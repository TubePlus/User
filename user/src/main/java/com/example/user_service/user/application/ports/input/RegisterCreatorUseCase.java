//package com.example.user_service.user.application.ports.input;
//
//import com.example.user_service.user.application.ports.output.dto.RegisterCreatorDto;
//import lombok.Builder;
//import lombok.Getter;
//
//public interface RegisterCreatorUseCase {
//
//    RegisterCreatorDto registerCreator(RegisterCreatorQuery registerCreatorQuery);
//
//    @Getter
//    @Builder
//    class RegisterCreatorQuery {
//
//        private String uuid;
//        private String categoryName;
//
//        public static RegisterCreatorQuery toQuery(String uuid, String categoryName) {
//
//            return RegisterCreatorQuery.builder()
//                    .uuid(uuid)
//                    .categoryName(categoryName)
//                    .build();
//        }
//    }
//}
