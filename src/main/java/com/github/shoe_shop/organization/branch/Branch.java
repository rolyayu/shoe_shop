package com.github.shoe_shop.organization.branch;

import com.github.shoe_shop.shared.CreateDateAuditableEntity;
import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.user.user_info.UserInfo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(BranchIdClass.class)
@Table(name = "branches")
public class Branch extends CreateDateAuditableEntity {

    @Column(name = "branch_id", nullable = false, unique = true)
    private String branchId = UUID.randomUUID().toString();

    @Id
    @Column(name = "branch_no", unique = true, nullable = false)
    private String branchNo;

    @OneToOne
    @JoinColumn(name = "branch_head_info_id", referencedColumnName = "id", nullable = false)
    private UserInfo branchHead;

    @Column(name = "branch_address", nullable = false)
    private String branchAddress;

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "organization_unp", referencedColumnName = "organization_unp")
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BranchType type;
}
