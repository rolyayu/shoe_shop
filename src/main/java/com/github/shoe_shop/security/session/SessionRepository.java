package com.github.shoe_shop.security.session;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SessionRepository extends CrudRepository<Session, Long> {
    @Modifying
    @Query(value = """
                delete from Session s where s.lastPerformedActionDate < ?1
            """)
    Long deleteExpiredSessions(final LocalDateTime expirationDate);
}
