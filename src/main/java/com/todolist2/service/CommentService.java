package com.todolist2.service;

import com.todolist2.dto.commentdto.CreateCommentRequestDto;
import com.todolist2.dto.commentdto.CreateCommentResponseDto;
import com.todolist2.dto.commentdto.GetCommentResponseDto;
import com.todolist2.entity.Comment;
import com.todolist2.entity.Schedule;
import com.todolist2.entity.User;
import com.todolist2.exception.ForbiddenException;
import com.todolist2.exception.NotFoundException;
import com.todolist2.repository.CommentRepository;
import com.todolist2.repository.ScheduleRepository;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponseDto save(Long scheduleId, CreateCommentRequestDto request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("유저가 존재하지 않습니다.")
        );
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정이 존재하지 않습니다.")
        );

        Comment comment = new Comment(user, schedule, request.getCommentContents());
        Comment savedComment = commentRepository.save(comment);
        return CreateCommentResponseDto.from(savedComment);
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponseDto> findAll(Long scheduleId) {
        return commentRepository.findAllBySchedule_Id(scheduleId)
                .stream()
                .map(GetCommentResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteOne(Long commentId, String email) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getUser().getEmail().equals(email)) {
            throw new ForbiddenException("본인이 작성한 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
