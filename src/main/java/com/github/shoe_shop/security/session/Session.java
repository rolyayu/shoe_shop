package com.github.shoe_shop.security.session;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.organization.workstations.Workstation;
import com.github.shoe_shop.user.user_card.UserCard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "sessions")
public class Session extends CreateDateAuditableEntity {
    @Id
    @SequenceGenerator(name = "session_seq", sequenceName = "session_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq")
    private Long id;

    @Column(name = "last_performed_action_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastPerformedActionDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "workstation_ip", referencedColumnName = "ip", nullable = false)
    private Workstation workstation;

    @ManyToOne
    @JoinColumn(name = "user_card_number", referencedColumnName = "card_no")
    private UserCard card;
}
