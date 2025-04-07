package com.github.shoe_shop.user.user_card;

import com.github.shoe_shop.exceptions.EntityAlreadyExistsException;
import com.github.shoe_shop.organization.Organization;
import com.github.shoe_shop.organization.OrganizationService;
import com.github.shoe_shop.organization.workstations.Workstation;
import com.github.shoe_shop.organization.workstations.WorkstationService;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCardService {
    private final UserCardRepository userCardRepository;

    private final WorkstationService workstationService;

    private final OrganizationService organizationService;

    private final UserInfoService infoService;

    @Transactional
    public UserCard createCard(final UserCard cardToCreate) {
        if (userCardRepository.existsById(cardToCreate.getCardNo())) {
            throw new EntityAlreadyExistsException("Card with number " + cardToCreate.getCardNo() + " already exists");
        }

        Organization supposedOrganization = cardToCreate.getSupposedOrganization();
        if (supposedOrganization == null) {
            throw new RuntimeException("No organization provided for card " + cardToCreate.getCardNo());
        }
        supposedOrganization = organizationService.findByUnp(supposedOrganization.getUnp());
        cardToCreate.setSupposedOrganization(supposedOrganization);

        if (cardToCreate.getAttachedWorker() != null) {
            final UserInfo info = infoService.getInfoById(cardToCreate.getAttachedWorker().getId());
            if (!info.getOrganization().equals(supposedOrganization)) {
                throw new RuntimeException("Organizations are not matched for card " + cardToCreate.getCardNo());
            }
            cardToCreate.setAttachedWorker(info);
        }

        Workstation workstation = cardToCreate.getWorkstation();
        if (workstation != null) {
            workstation = workstationService.findByIP(workstation.getIp());
            if (!workstation.getOrganization().equals(supposedOrganization)) {
                throw new RuntimeException("Organizations are not matched for workstation with IP " + workstation.getIp());
            }
            cardToCreate.setWorkstation(workstation);
        }

        return userCardRepository.save(cardToCreate);
    }

    @Transactional
    public UserCard updateCard(final UserCard cardToUpdate) {
        final UserCard foundedCard = findByCardNo(cardToUpdate.getCardNo());
        if (cardToUpdate.getAttachedWorker() != null) {
            final UserInfo info = infoService.getInfoById(cardToUpdate.getAttachedWorker().getId());
            foundedCard.setAttachedWorker(info);
        }
        if (cardToUpdate.getWorkstation() != null) {
            final Workstation workstation = workstationService.findByIP(cardToUpdate.getWorkstation().getIp());
            foundedCard.setWorkstation(workstation);
        }
        return userCardRepository.save(foundedCard);
    }

    public UserCard findByCardNo(final String cardNo) {
        return userCardRepository.findById(cardNo)
                .orElseThrow(() -> new EntityNotFoundException("Card with number " + cardNo + " not found"));
    }
}
