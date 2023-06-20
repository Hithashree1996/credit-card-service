package com.credit.card.creditcardservice.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a customer domain. This class encapsulates the information related
 * to a customer request
 */
@Data
public class Customer {
	private UUID id;
	private String customerName;
	private String address;
	private String mobileNumber;
	private String communicationInfo;
	private String customerId;
	private LocalDate updatedDate;
	private LocalDateTime updatedTime;
}
