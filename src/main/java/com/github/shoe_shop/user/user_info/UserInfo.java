package com.github.shoe_shop.user.user_info;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.user.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Setter
@Getter
public class UserInfo extends CreateDateAuditableEntity {

    @Id
    @SequenceGenerator(name = "user_info_seq", sequenceName = "user_info_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "organization_unp", referencedColumnName = "organization_unp")
    private Organization organization;

    @Column(name = "job_position", nullable = false)
    private String jobPosition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo userInfo)) return false;
        if (user == null) {
            return false;
        }
        return Objects.equals(user.getId(), userInfo.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
