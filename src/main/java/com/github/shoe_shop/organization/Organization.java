package com.github.shoe_shop.organization;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
    @Column(name = "organization_unp", nullable = false, unique = true)
    private String unp;

    @Column(name = "organization_name", nullable = false)
    private String organizationName;


    @Column(name = "organization_address", nullable = false)
    private String address;
}
