package com.credit.card.creditcardservice;

import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.entity.CreditCardRequestEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardRequestEntityTests {

	@Test
	void getCreditCardRequestEntity_ValidCreditCardRequest_PopulatesFieldsCorrectly() {
		// Arrange
		CreditCardRequest creditCardRequest = new CreditCardRequest();
		UUID customerUuid = UUID.randomUUID();
		creditCardRequest.setCustomerUuid(customerUuid);
		creditCardRequest.setStatus(RecordStatus.APPROVED);
		creditCardRequest.setVerificationComments("Verification in progress");
		creditCardRequest.setNotificationStatus(NotificationStatus.NEW);

		CreditCardRequestEntity creditCardRequestEntity = new CreditCardRequestEntity();

		// Act
		CreditCardRequestEntity result = creditCardRequestEntity.getCreditCardRequestEntity(creditCardRequest);

		// Assert
		assertEquals(customerUuid, result.getCustomerUuid());
		assertEquals(RecordStatus.APPROVED, result.getStatus());
		assertEquals("Verification in progress", result.getVerificationComments());
		assertEquals(NotificationStatus.NEW, result.getNotificationStatus());
	}

	@Test
	void getCreditCardRequest_ValidCreditCardRequest_PopulatesFieldsCorrectly() {
		// Arrange
		UUID customerUuid = UUID.randomUUID();
		CreditCardRequestEntity creditCardRequestEntity = new CreditCardRequestEntity();
		creditCardRequestEntity.setCustomerUuid(customerUuid);
		creditCardRequestEntity.setStatus(RecordStatus.APPROVED);
		creditCardRequestEntity.setVerificationComments("Verification in progress");
		creditCardRequestEntity.setNotificationStatus(NotificationStatus.NEW);
		LocalDate updatedDate = LocalDate.now();
		creditCardRequestEntity.setUpdatedDate(updatedDate);
		LocalDateTime updatedTime = LocalDateTime.now();
		creditCardRequestEntity.setUpdatedTime(updatedTime);
		creditCardRequestEntity.setId(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));

		// Act
		CreditCardRequest result = creditCardRequestEntity.getCreditCardRequest();

		// Assert
		assertEquals(customerUuid, result.getCustomerUuid());
		assertEquals(RecordStatus.APPROVED, result.getStatus());
		assertEquals("Verification in progress", result.getVerificationComments());
		assertEquals(NotificationStatus.NEW, result.getNotificationStatus());
		assertEquals(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"), result.getId());
	}

}
