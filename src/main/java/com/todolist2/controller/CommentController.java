package com.todolist2.controller;

import com.todolist2.dto.commentdto.CreateCommentRequestDto;
import com.todolist2.dto.commentdto.CreateCommentResponseDto;
import com.todolist2.dto.commentdto.GetCommentResponseDto;
import com.todolist2.dto.userDto.LoginResponseDto;
import com.todolist2.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글 controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성 기능(로그인 된 계정이 있어야 댓글 작성 가능)
     * @param scheduleId
     * @param request
     * @param session
     * @return
     */
    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> create(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequestDto request,
            HttpSession session
    ) {
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.save(scheduleId, request, loginUser.getEmail()));
    }

    /**
     * 댓글 조회 기능(해당 일정에 있는 댓글 전체 조회)
     * @param scheduleId
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GetCommentResponseDto>> getAll(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }

    /**
     * 댓글 삭제 controller
     * @param scheduleId
     * @param commentId
     * @param session
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpSession session
    ) {
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        commentService.deleteOne(commentId, loginUser.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
