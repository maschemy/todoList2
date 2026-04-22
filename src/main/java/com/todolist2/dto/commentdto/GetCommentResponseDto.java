package com.todolist2.dto.commentdto;

import com.todolist2.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponseDto {
    private final Long id;
    private final String userName;
    private final String commentContents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentResponseDto(Long id, String userName, String commentContents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userName = userName;
        this.commentContents = commentContents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static GetCommentResponseDto from(Comment comment) {
        return new GetCommentResponseDto(
                comment.getId(),
                comment.getUser().getUserName(),
                comment.getCommentContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
