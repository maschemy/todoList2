package com.todolist2.service;

import com.todolist2.dto.scheduleDto.*;
import com.todolist2.entity.Schedule;
import com.todolist2.entity.User;
import com.todolist2.exception.ForbiddenException;
import com.todolist2.exception.NotFoundException;
import com.todolist2.repository.ScheduleRepository;
import com.todolist2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 일정 관리 service
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정생성, email로 로그인한 유저가 있으면 생성가능
     * @param request
     * @param email
     * @return
     */
    @Transactional
    public CreateScheduleResponseDto save(CreateScheduleRequestDto request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("해당 유저를 찾을 수 없습니다.")
        );
        Schedule schedule = new Schedule(
                user,
                request.getTitle(),
                request.getContents()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return CreateScheduleResponseDto.from(savedSchedule);
    }

    /**
     * 일정 전체 조회
     * @param userName
     * @return
     */
    @Transactional(readOnly = true)
    public List<GetOneScheduleResponseDto> findAll(String userName) {
        List<Schedule> schedules;

        if (userName != null) {
            schedules = scheduleRepository.findAllByUser_UserName(userName);
        } else {
            schedules = scheduleRepository.findAll();
        }
        return schedules.stream()
                .map(GetOneScheduleResponseDto::from)
                .toList();
    }

    /**
     * 일정 단건 조회
     * @param scheduleId
     * @return
     */
    @Transactional(readOnly = true)
    public GetOneScheduleResponseDto findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정이 존재하지 않습니다.")
        );
        return GetOneScheduleResponseDto.from(schedule);
    }

    /**
     * 일정 업데이트: 일정이 존재하는지, 로그인한 유저랑 일정을 작성한 유저랑 비교
     * @param request
     * @param scheduleId
     * @param email
     * @return
     */
    @Transactional
    public UpdateScheduleResponseDto updateOne(UpdateScheduleRequestDto request, Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정이 존재하지 않습니다.")
        );
        if(!schedule.getUser().getEmail().equals((email))){
            throw new ForbiddenException("본인이 작성한 일정만 수정할 수 있습니다.");
        }

        schedule.update(
                request.getTitle(),
                request.getContents()
        );

        return UpdateScheduleResponseDto.from(schedule);
    }

    /**
     * 일정 삭제 기능: 일정이 존재하는지 체크, 본인이 작성한 일정만 삭제 가능
     * @param scheduleId
     * @param email
     */
    @Transactional
    public void deleteOne(Long scheduleId, String email) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NotFoundException("일정이 존재하지 않습니다.")
        );

        if(!schedule.getUser().getEmail().equals(email)){
            throw new ForbiddenException("본인이 작성한 일정만 삭제할 수 있습니다.");
        }
        scheduleRepository.delete(schedule);
    }


}
