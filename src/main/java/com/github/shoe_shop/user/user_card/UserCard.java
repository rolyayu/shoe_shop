package com.github.shoe_shop.user.user_card;

import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.organization.workstations.Workstation;
import com.github.shoe_shop.user.user_info.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_cards")
public class UserCard {
    @Id
    @Column(name = "card_no")
    private String cardNo;

    @ManyToOne
    @JoinColumn(name = "workstation_ip", referencedColumnName = "ip", nullable = true)
    private Workstation workstation;

    @OneToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id", nullable = true)
    private UserInfo attachedWorker;

    @JoinColumn(name = "supposed_org_unp", referencedColumnName = "organization_unp", nullable = false)
    @ManyToOne
    private Organization supposedOrganization;
}
