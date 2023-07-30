package kr.anabada.anabadaserver.common.repository;

import kr.anabada.anabadaserver.common.dto.CommentResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponse> getCommentsByPost(String postType, Long postId);
}
