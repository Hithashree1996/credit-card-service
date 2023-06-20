package com.credit.card.creditcardservice.infrastructure.repo.port.impl;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.entity.CreditCardRequestEntity;
import com.credit.card.creditcardservice.infrastructure.repo.CreditCardRequestRepo;
import com.credit.card.creditcardservice.infrastructure.repo.port.CreditCardRequestRepoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the CreditCardRequest Repository Port.
 * Provides methods for managing CreditCard Requests using the CreditCardRequestRepository.
 */

@Component
public class CreditCardRequestAdapter implements CreditCardRequestRepoPort {

	@Autowired
	private CreditCardRequestRepo creditCardRequestRepo;

	@Override
	public CreditCardRequest save(CreditCardRequest creditCardRequest) {
		CreditCardRequestEntity creditCardRequestEntity = new CreditCardRequestEntity()
				.getCreditCardRequestEntity(creditCardRequest);
		return creditCardRequestRepo.save(creditCardRequestEntity).getCreditCardRequest();
	}

	@Override
	public List<CreditCardRequestInfoDto> findByCustomerId(String customerId) {
		return creditCardRequestRepo.findByCustomerId(customerId);
	}

	@Override
	public List<CreditCardRequest> findByNotificationStatus(NotificationStatus notificationStatus) {
		List<CreditCardRequestEntity> creditCardRequestEntityList = creditCardRequestRepo
				.findByNotificationStatus(notificationStatus);
		return creditCardRequestEntityList.stream().map(CreditCardRequestEntity::getCreditCardRequest)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public int update(UUID applicationUuid, RecordStatus status, String comment) {
		return creditCardRequestRepo.updateStatusAndComment(status, comment, NotificationStatus.NEW, applicationUuid,
				LocalDate.now(), LocalDateTime.now());
	}

	@Override
	@Transactional
	public void updateNotificationStatus(UUID applicationUuid, NotificationStatus notificationStatus) {
		creditCardRequestRepo.updateNotificationStatus(notificationStatus, applicationUuid, LocalDate.now(),
				LocalDateTime.now());
	}

}
