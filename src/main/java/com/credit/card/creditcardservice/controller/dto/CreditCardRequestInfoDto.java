package com.credit.card.creditcardservice.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.credit.card.creditcardservice.enums.RecordStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Represents the information of a credit card request.
 */
@Data
public class CreditCardRequestInfoDto {

	private RecordStatus status;

	private String verificationComments;

	private String customerName;

	private String address;

	private String mobileNumber;

	private String communicationInfo;

	private String customerId;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate createdDate;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime createdTime;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate updatedDate;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime updatedTime;

	private UUID applicationUuid;

	 /**
     * Constructs a new CreditCardRequestInfoDto with the specified information.
     *
     * @param status               The status of the credit card request.
     * @param verificationComments The verification comments associated with the request.
     * @param customerName         The name of the customer.
     * @param address              The address of the customer.
     * @param mobileNumber         The mobile number of the customer.
     * @param communicationInfo    The communication info of the customer.
     * @param customerId           The ID of the customer.
     * @param createdDate          The date when the request was created.
     * @param createdTime          The time when the request was created.
     * @param updatedDate          The date when the request was last updated.
     * @param updatedTime          The time when the request was last updated.
     * @param applicationUuid      The UUID of the credit card application.
     */
    public CreditCardRequestInfoDto(RecordStatus status, String verificationComments, String customerName,
                                    String address, String mobileNumber, String communicationInfo, String customerId,
                                    LocalDate createdDate, LocalDateTime createdTime, LocalDate updatedDate,
                                    LocalDateTime updatedTime, UUID applicationUuid) {
        this.status = status;
        this.verificationComments = verificationComments;
        this.customerName = customerName;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.communicationInfo = communicationInfo;
        this.customerId = customerId;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
        this.updatedDate = updatedDate;
        this.updatedTime = updatedTime;
        this.applicationUuid = applicationUuid;
    }
	
    /**
     * Returns the display string of the status.
     *
     * @return The display string of the status, or null if the status is null.
     */
	public String statusDisplayStr() {
		if (status != null) {
			return status.getDisplayName();
		}
		return null;
	}

}
