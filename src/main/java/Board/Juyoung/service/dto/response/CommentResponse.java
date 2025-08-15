package Board.Juyoung.service.dto.response;


public record CommentResponse(
    Long commentId,
    Long memberId,
    String nickname,
    String content
) {

    public static CommentResponse of(Long commentId, Long memberId, String nickname, String content) {
        return new CommentResponse(commentId, memberId, nickname, content);
    }
}