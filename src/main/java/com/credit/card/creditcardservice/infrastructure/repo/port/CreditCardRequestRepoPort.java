package com.credit.card.creditcardservice.infrastructure.repo.port;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;

import java.util.List;
import java.util.UUID;

/**
 * Interface for the Credit Card Request Repository Port.
 * Provides methods for managing credit card requests.
 */
public interface CreditCardRequestRepoPort {

	CreditCardRequest save(CreditCardRequest creditCardRequest);

	List<CreditCardRequestInfoDto> findByCustomerId(String customerId);

	List<CreditCardRequest> findByNotificationStatus(NotificationStatus notificationStatus);

	int update(UUID applicationUuid, RecordStatus status, String comment);

	void updateNotificationStatus(UUID applicationUuid, NotificationStatus notificationStatus);
}
