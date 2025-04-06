package com.github.shoe_shop.security.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionProperties sessionProperties;

    private final SessionRepository sessionRepository;

    public long deleteExpiredSessions() {
        final LocalDateTime expirationDate = buildExpirationDate();
        return sessionRepository.deleteExpiredSessions(expirationDate);
    }

    private LocalDateTime buildExpirationDate() {
        return LocalDateTime.ofInstant(Instant.now().minusMillis(sessionProperties.getExpirationMillis()), ZoneId.systemDefault());
    }
}
