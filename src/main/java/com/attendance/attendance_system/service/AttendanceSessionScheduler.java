package com.attendance.attendance_system.service;

import com.attendance.attendance_system.model.AttendanceSession;
import com.attendance.attendance_system.repository.AttendanceSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AttendanceSessionScheduler {

    private final AttendanceSessionRepository sessionRepo;

    public AttendanceSessionScheduler(AttendanceSessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    /*
     * Runs every 1 minute
     * Closes expired attendance sessions
     */
    @Scheduled(fixedRate = 60000)
    public void closeExpiredSessions() {

        List<AttendanceSession> activeSessions =
                sessionRepo.findByActiveTrue();

        for (AttendanceSession session : activeSessions) {

            LocalDateTime expiryTime =
                    session.getStartedAt()
                            .plusMinutes(session.getDurationMinutes());

            if (LocalDateTime.now().isAfter(expiryTime)) {

                session.setActive(false);
                session.setEndedAt(LocalDateTime.now());

                sessionRepo.save(session);
            }
        }
    }
}