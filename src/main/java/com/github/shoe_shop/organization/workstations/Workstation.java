package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.organization.Organization;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workstations")
public class Workstation extends CreateDateAuditableEntity {

    @Id
    private String ip;

    @ManyToOne
    @JoinColumn(name = "organization_unp", referencedColumnName = "organization_unp", nullable = false)
    private Organization organization;

}
