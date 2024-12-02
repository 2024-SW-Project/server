/*
package com.example.subwayserver.controller;

import com.example.subwayserver.entity.CalendarRoute;
import com.example.subwayserver.service.CalendarService;
import com.example.subwayserver.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subway/save/calendar")
@CrossOrigin(origins = "http://localhost:5173")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public Map<String, Object> getCalendarDates(@RequestHeader("Authorization") String token) {
        Long userId = extractUserIdFromToken(token);

        List<CalendarRoute> dates = calendarService.getCalendarDates(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("data", Map.of("dates", dates.stream()
                .map(CalendarRoute::getScheduledDate)
                .distinct()
                .toList()));
        return response;
    }

    @GetMapping(params = "date")
    public Map<String, Object> getRoutesByDate(@RequestHeader("Authorization") String token,
                                               @RequestParam String date) {
        Long userId = extractUserIdFromToken(token);
        LocalDate scheduledDate = LocalDate.parse(date);

        List<CalendarRoute> routes = calendarService.getRoutesByDate(userId, scheduledDate);

        Map<String, Object> response = new HashMap<>();
        response.put("data", Map.of("routes", routes));
        return response;
    }

    private Long extractUserIdFromToken(String token) {
        return JwtUtil.extractUserIdFromToken(token);
    }
}
*/




package com.example.subwayserver_1.controller;

import com.example.subwayserver_1.entity.CalendarRoute;
import com.example.subwayserver_1.service.CalendarService;
import com.example.subwayserver_1.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subway/save/calendar")
@CrossOrigin(origins = "http://localhost:5173")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping(params = "date")
    public Map<String, Object> getRoutesByDate(@RequestHeader("Authorization") String token,
                                               @RequestParam String date) {
        Long userId = extractUserIdFromToken(token);
        LocalDate scheduledDate = LocalDate.parse(date);

        // 특정 날짜의 일정 가져오기
        List<CalendarRoute> routes = calendarService.getRoutesByDate(userId, scheduledDate);

        // 필요한 데이터만 필터링
        List<Map<String, Object>> filteredRoutes = routes.stream()
                .map(route -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("start_station_name", route.getStartStationName());
                    map.put("end_station_name", route.getEndStationName());
                    map.put("is_climate_card_eligible", route.getIsClimateCardEligible());
                    map.put("scheduled_date", route.getScheduledDate().toString());
                    map.put("reminder_time", route.getReminderTime().toString());
                    return map;
                })
                .collect(Collectors.toList());

        // 응답 생성
        Map<String, Object> response = new HashMap<>();
        response.put("data", Map.of("routes", filteredRoutes));
        return response;
    }

    private Long extractUserIdFromToken(String token) {
        return JwtUtil.extractUserIdFromToken(token);
    }
}