package com.credit.card.creditcardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.credit.card.creditcardservice.controller.dto.CustomerDto;
import com.credit.card.creditcardservice.domain.Customer;

public class CustomerDtoTests {

	@Test
	void getCustomer_ValidCustomerDto_ReturnsCustomerObjectWithCorrectValues() {
		// Arrange
		CustomerDto customerDto = new CustomerDto();
		customerDto.setAddress("123 Main St");
		customerDto.setCommunicationInfo("Email: example@example.com");
		customerDto.setCustomerName("John Doe");
		customerDto.setMobileNumber("1234567890");

		// Act
		Customer result = customerDto.toCustomer();

		// Assert
		assertNotNull(result);
		assertEquals("123 Main St", result.getAddress());
		assertEquals("Email: example@example.com", result.getCommunicationInfo());
		assertEquals("John Doe", result.getCustomerName());
		assertEquals("1234567890", result.getMobileNumber());
	}
}
