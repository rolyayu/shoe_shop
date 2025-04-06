package com.github.shoe_shop.security.session;

import com.github.shoe_shop.organization.workstations.WorkstationService;
import jakarta.persistence.EntityNotFoundException;
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

    private final WorkstationService workstationService;

    public long deleteExpiredSessions() {
        final LocalDateTime expirationDate = buildExpirationDate();
        return sessionRepository.deleteExpiredSessions(expirationDate);
    }

    public Session findActiveSessionByIP(final String ip) {
        return sessionRepository.findActiveSessionByIP(ip, buildExpirationDate())
                .orElseThrow(() -> new EntityNotFoundException("Active session for IP " + ip + " not found"));
    }

    public Session createSession(final String ip, final String cardNo) {
        //TODO implement
        return null;
    }

    public void updateLastPerformedActionDate(final Session session) {
        session.setLastPerformedActionDate(LocalDateTime.now());
        sessionRepository.save(session);
    }

    private LocalDateTime buildExpirationDate() {
        return LocalDateTime.ofInstant(Instant.now().minusMillis(sessionProperties.getExpirationMillis()), ZoneId.systemDefault());
    }
}
