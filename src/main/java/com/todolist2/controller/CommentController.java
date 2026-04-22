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

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {
    private final CommentService commentService;

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

    @GetMapping
    public ResponseEntity<List<GetCommentResponseDto>> getAll(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }

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
