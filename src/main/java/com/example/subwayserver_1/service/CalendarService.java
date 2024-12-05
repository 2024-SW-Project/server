package com.example.subwayserver_1.service;

import com.example.subwayserver_1.entity.CalendarRoute;
import com.example.subwayserver_1.repository.CalendarRouteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CalendarService {

    private final CalendarRouteRepository calendarRouteRepository;

    public CalendarService(CalendarRouteRepository calendarRouteRepository) {
        this.calendarRouteRepository = calendarRouteRepository;
    }

    // 사용자 ID로 저장된 모든 일정 조회
    public List<CalendarRoute> getCalendarDates(Long userId) {
        return calendarRouteRepository.findByUserId(userId);
    }

    // 사용자 ID와 특정 날짜에 해당하는 일정 조회
    public List<CalendarRoute> getRoutesByDate(Long userId, LocalDate date) {
        return calendarRouteRepository.findByUserIdAndScheduledDate(userId, date);
    }

    // 특정 ID로 일정 삭제
    public boolean deleteCalendarRoute(Long id) {
        if (calendarRouteRepository.existsById(id)) { // ID 존재 여부 확인
            calendarRouteRepository.deleteById(id);   // 해당 ID 삭제
            return true;
        }
        return false; // ID가 없으면 false 반환
    }
}
