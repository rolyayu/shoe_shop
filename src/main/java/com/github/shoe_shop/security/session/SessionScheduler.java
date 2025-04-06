package com.github.shoe_shop.security.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionScheduler {

    private final SessionService sessionService;

    @Scheduled(cron = "* */20 9-18 * * *")
    public void deleteExpiredSessions() {
        log.info("Start deleting expired sessions...");
        final long deletedSessionsCount = sessionService.deleteExpiredSessions();
        log.info("Deleted expired sessions: {}", deletedSessionsCount);
    }
}
