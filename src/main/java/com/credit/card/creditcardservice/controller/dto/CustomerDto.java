package com.credit.card.creditcardservice.controller.dto;

import com.credit.card.creditcardservice.domain.Customer;
import lombok.Data;

/**
 * Represents a DTO (Data Transfer Object) for a customer.
 */
@Data
public class CustomerDto {
	private String customerName;
	private String address;
	private String mobileNumber;
	private String communicationInfo;

	 /**
     * Converts the CustomerDto to a Customer domain object.
     *
     * @return The Customer domain object populated with the data from the CustomerDto.
     */
	public Customer toCustomer() {
		Customer customer = new Customer();
		customer.setAddress(this.address);
		customer.setCommunicationInfo(this.communicationInfo);
		customer.setCustomerName(this.customerName);
		customer.setMobileNumber(this.mobileNumber);
		return customer;
	}
}
