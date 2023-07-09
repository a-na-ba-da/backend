package kr.anabada.anabadaserver.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.common.dto.CommentRequest;
import kr.anabada.anabadaserver.common.service.CommentService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "common", description = "각 도메인에서 공통으로 사용되는 API")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{postType}/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "댓글 작성 API", description = "댓글을 작성합니다.")
    public void writeNewComment(@CurrentUser User user,
                                @Parameter(description = "댓글을 달 게시판 타입", required = true,
                                        examples = {
                                                @ExampleObject(name = "같이 사요", value = "buy-together"),
                                                @ExampleObject(name = "같이 알아요", value = "know-together"),
                                        })
                                @NotNull(message = "댓글을 달 게시판 타입을 입력해주세요.")
                                @PathVariable String postType,
                                @Parameter(description = "댓글을 작성할 게시물의 id", required = true, example = "1")
                                @NotNull(message = "댓글을 달 게시물 id를 입력해주세요") @PathVariable Long postId,
                                @Valid @RequestBody CommentRequest commentRequest) {
        if (user == null) throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        commentService.writeNewComment(user, postType, postId, commentRequest);
    }

//    @GetMapping("/comment/{postType}/{postId}")
//    @Operation(summary = "댓글 조회 API", description = "댓글을 조회합니다.")
//    public GlobalResponse<CommentResponse> readComments(
//            @Parameter(description = "댓글을 달 게시판 타입", required = true,
//                    examples = {
//                            @ExampleObject(name = "같이 사요", value = "buy-together"),
//                            @ExampleObject(name = "같이 알아요", value = "know-together"),
//                    })
//            @NotNull(message = "조회 할 게시판 타입을 입력해주세요.")
//            @PathVariable String postType,
//            @NotNull(message = "조회 할 게시물 id를 입력해주세요") @PathVariable Long postId) {
//        commentService.getPostComments(postType, postId);
//
//        return null;
//    }
}
