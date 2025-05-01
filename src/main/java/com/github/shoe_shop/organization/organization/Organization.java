package com.github.shoe_shop.organization.organization;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.user.user_info.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "organizations")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Organization extends CreateDateAuditableEntity {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "organization_unp", nullable = false)
    private String unp;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;


    @Column(name = "organization_address", nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "organization_head_info_id")
    private UserInfo organizationHead;
}
