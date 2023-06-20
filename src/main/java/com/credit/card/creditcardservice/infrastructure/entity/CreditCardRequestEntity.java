package com.credit.card.creditcardservice.infrastructure.entity;

import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an entity for credit card entity.
 * Extends the BaseEntity class and provides additional fields and methods for credit card requests.
 */
@Entity
@Table(name = "credit_card_requests")
@Data
@EqualsAndHashCode(callSuper = false)
public class CreditCardRequestEntity extends BaseEntity {

	@Column(name = "customer_uuid")
	private UUID customerUuid;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private RecordStatus status = RecordStatus.NEW;

	@Column(name = "verification_comments")
	private String verificationComments;

	@Column(name = "notification_status")
	@Enumerated(EnumType.STRING)
	private NotificationStatus notificationStatus;

	@Column(name = "updated_date", columnDefinition = "DATE")
	private LocalDate updatedDate;

	@Column(name = "updated_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime updatedTime;

	/**
	 * Retrieves the CreditCardRequestEntity instance with data from the given CreditCardRequest domain.
	 *
	 * @param creditCardRequest the CreditCardRequest object
	 * @return the CreditCardRequestEntity instance
	 */
	public CreditCardRequestEntity getCreditCardRequestEntity(CreditCardRequest creditCardRequest) {
		this.customerUuid = creditCardRequest.getCustomerUuid();
		if (creditCardRequest.getStatus() != null) {
			this.status = creditCardRequest.getStatus();
		}
		this.verificationComments = creditCardRequest.getVerificationComments();
		this.notificationStatus = creditCardRequest.getNotificationStatus();
		this.customerId = creditCardRequest.getCustomerId();
		return this;
	}

	/**
	 * Retrieves the CreditCardRequest domain based on the data from this CreditCardRequestEntity instance.
	 *
	 * @return the CreditCardRequest object
	 */
	public CreditCardRequest getCreditCardRequest() {
		CreditCardRequest creditCardRequest = new CreditCardRequest();
		creditCardRequest.setCustomerUuid(this.customerUuid);
		creditCardRequest.setNotificationStatus(this.notificationStatus);
		creditCardRequest.setStatus(this.status);
		creditCardRequest.setVerificationComments(this.verificationComments);
		creditCardRequest.setId(super.id);
		creditCardRequest.setCustomerId(this.customerId);
		return creditCardRequest;

	}

}
