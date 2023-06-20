package com.credit.card.creditcardservice.domain;

import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

/**
 * Represents a credit card domain.
 * This class encapsulates the information related to a credit card request,
 * including the ID, customer UUID, status, verification comments,
 * notification status, updated date, and updated time.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreditCardRequest {
	private UUID id;
	private UUID customerUuid;
	private RecordStatus status;
	private String verificationComments;
	private NotificationStatus notificationStatus;
	private String customerId;
}
