package com.github.shoe_shop.user.user_card;

import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.organization.workstations.Workstation;
import com.github.shoe_shop.user.user_card.dto.CreateCardDto;
import com.github.shoe_shop.user.user_info.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserCardDtoMapper {
    public UserCard mapToEntity(final CreateCardDto dto) {
        final UserCard card = new UserCard();
        card.setCardNo(dto.cardNo());

        final Organization supposedOrg = new Organization();
        supposedOrg.setUnp(dto.unp());
        card.setSupposedOrganization(supposedOrg);

        if (dto.workerId() != null) {
            final UserInfo worker = new UserInfo();
            worker.setId(dto.workerId());
            card.setAttachedWorker(worker);
        }

        if (dto.workstationIP() != null) {
            final Workstation workstation = new Workstation();
            workstation.setIp(dto.workstationIP());
            card.setWorkstation(workstation);
        }

        return card;
    }
}
