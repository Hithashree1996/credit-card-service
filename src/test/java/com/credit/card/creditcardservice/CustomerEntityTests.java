package com.credit.card.creditcardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.infrastructure.entity.CustomerEntity;

public class CustomerEntityTests {

	@Test
	void getCustomerDomain_ValidCustomerEntity_ReturnsCustomerObjectWithCorrectValues() {
		// Arrange
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setAddress("123 Main St");
		customerEntity.setCommunicationInfo("Email: example@example.com");
		customerEntity.setCustomerId("CUST-123");
		customerEntity.setCustomerName("John Doe");
		customerEntity.setMobileNumber("1234567890");
		customerEntity.setId(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));

		// Act
		Customer result = customerEntity.getCustomerDomain();

		// Assert
		assertNotNull(result);
		assertEquals("123 Main St", result.getAddress());
		assertEquals("Email: example@example.com", result.getCommunicationInfo());
		assertEquals("CUST-123", result.getCustomerId());
		assertEquals("John Doe", result.getCustomerName());
		assertEquals("1234567890", result.getMobileNumber());
		assertEquals(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"), result.getId());
	}

	@Test
	void getCustomer_ValidCustomer_ReturnsCustomerEntityWithCorrectValues() {
		// Arrange
		Customer customer = new Customer();
		customer.setAddress("123 Main St");
		customer.setCommunicationInfo("Email: example@example.com");
		customer.setCustomerId("CUST-123");
		customer.setCustomerName("John Doe");
		customer.setMobileNumber("1234567890");

		CustomerEntity customerEntity = new CustomerEntity();

		// Act
		CustomerEntity result = customerEntity.getCustomer(customer);

		// Assert
		assertNotNull(result);
		assertEquals("123 Main St", result.getAddress());
		assertEquals("Email: example@example.com", result.getCommunicationInfo());
		assertEquals("CUST-123", result.getCustomerId());
		assertEquals("John Doe", result.getCustomerName());
		assertEquals("1234567890", result.getMobileNumber());
	}
}
