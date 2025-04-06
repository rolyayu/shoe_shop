package com.github.shoe_shop.security.session;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    @Modifying
    @Query(value = """
                delete from Session s where s.lastPerformedActionDate < ?1
            """)
    Long deleteExpiredSessions(final LocalDateTime expirationDate);

    @Query(value = """
                select s from Session s where s.workstation.ip = ?1 and s.lastPerformedActionDate > ?2 
                order by s.lastPerformedActionDate desc limit 1
            """)
    Optional<Session> findActiveSessionByIP(final String ip, final LocalDateTime expirationDate);
}
