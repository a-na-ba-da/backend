package kr.anabada.anabadaserver.domain.change.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequest;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequestProduct;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class ChangeRequestResponse {
    @Parameter(description = "내가 요청한 교환 요청 목록")
    List<ReqByMe> requestedByMeList;
    @Parameter(description = "나에게 요청된 교환 요청 목록")
    List<ReqForMe> requestingForMeList;

    public ChangeRequestResponse(List<ReqByMe> requestedByMeList, List<ReqForMe> requestingForMeList) {
        this.requestedByMeList = requestedByMeList;
        this.requestingForMeList = requestingForMeList;
    }


    @Getter
    @NoArgsConstructor
    @Schema(description = "내가 요청한 교환 요청 목록")
    public static class ReqByMe {
        private long id;
        @Parameter(description = "내가 교환하고 싶은 상대방의 상품")
        private MyProductResponse targetProduct;
        @Parameter(description = "교환하고자 제안한 나의 물건 목록")
        private List<MyProductResponse> toChangeProducts;
        @Parameter(description = "내가 교환을 생성할때 보낸 메시지")
        private String message;
        @Parameter(description = "상대가 거절하면서 보낸 메시지 (거절한 경우에만)")
        private String rejectMessage;
        @Parameter(description = "교환 요청 상태")
        private ChangeRequestStatus status;
        @Parameter(description = "상대방 (교환 피신청자)")
        private UserDto owner;

        @Builder
        public ReqByMe(long id, MyProductResponse targetProduct, List<MyProductResponse> toChangeProducts, String message, String rejectMessage, ChangeRequestStatus status, UserDto owner) {
            this.id = id;
            this.targetProduct = targetProduct;
            this.toChangeProducts = toChangeProducts;
            this.message = message;
            this.rejectMessage = rejectMessage;
            this.status = status;
            this.owner = owner;
        }

        public static ReqByMe of(ChangeRequest changeRequest) {
            return ReqByMe.builder()
                    .id(changeRequest.getId())
                    .targetProduct(changeRequest.getTargetProduct().toResponseExcludeOwner())
                    .toChangeProducts(changeRequest.getToChangeProducts().stream()
                            .map(ChangeRequestProduct::getProduct)
                            .map(MyProduct::toResponseExcludeOwner)
                            .toList())
                    .message(changeRequest.getMessage())
                    .rejectMessage(changeRequest.getRejectMessage())
                    .status(changeRequest.getStatus())
                    .owner(changeRequest.getRequestee().toDto())
                    .build();
        }
    }


    @Getter
    @NoArgsConstructor
    @Schema(description = "나에게 제안 온 교환 요청 목록")
    public static class ReqForMe {
        private long id;
        @Parameter(description = "상대가 교환하자고 신청한 나의 상품")
        private MyProductResponse targetProduct;
        @Parameter(description = "상대가 교환하자고 신청한 상대의 상품 목록")
        private List<MyProductResponse> toChangeProducts;
        @Parameter(description = "상대가 교환하자고 신청한 메시지")
        private String message;
        @Parameter(description = "내가 거절하면서 보낸 메시지 (거절한 경우에만)")
        private String rejectMessage;
        @Parameter(description = "교환 요청 상태")
        private ChangeRequestStatus status;
        @Parameter(description = "교환 신청자")
        private UserDto requester;

        @Builder
        public ReqForMe(long id, MyProductResponse targetProduct, List<MyProductResponse> toChangeProducts, String message, String rejectMessage, ChangeRequestStatus status, UserDto requester) {
            this.id = id;
            this.targetProduct = targetProduct;
            this.toChangeProducts = toChangeProducts;
            this.message = message;
            this.rejectMessage = rejectMessage;
            this.status = status;
            this.requester = requester;
        }

        public static ReqForMe of(ChangeRequest request) {
            return new ReqForMe(
                    request.getId(),
                    request.getTargetProduct().toResponseExcludeOwner(),
                    request.getToChangeProducts().stream()
                            .map(ChangeRequestProduct::getProduct)
                            .map(MyProduct::toResponseExcludeOwner)
                            .toList(),
                    request.getMessage(),
                    request.getRejectMessage(),
                    request.getStatus(),
                    request.getRequester().toDto()
            );
        }
    }
}
