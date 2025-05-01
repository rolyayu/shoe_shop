package com.github.shoe_shop.organization.workstations;

import com.github.shoe_shop.base.CreateDateAuditableEntity;
import com.github.shoe_shop.organization.branch.Branch;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workstations")
public class Workstation extends CreateDateAuditableEntity {

    @Id
    @SequenceGenerator(name = "workstation_seq", sequenceName = "workstation_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workstation_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String ip;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "branch_id", referencedColumnName = "branch_id", nullable = false)
    private Branch branch;

}
