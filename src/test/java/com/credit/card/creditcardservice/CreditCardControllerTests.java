package com.credit.card.creditcardservice;

import com.credit.card.creditcardservice.controller.CreditCardController;
import com.credit.card.creditcardservice.controller.dto.CreditCardRequestPutDto;
import com.credit.card.creditcardservice.controller.dto.CustomerDto;
import com.credit.card.creditcardservice.controller.exceptions.ValidationException;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreditCardControllerTests {

	private CreditCardController creditCardController;

	@Mock
	private CustomerService customerService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		creditCardController = new CreditCardController(customerService);
	}

	@Test
	public void testApplyForCreditCard_ValidationException() {
		// Prepare test data
		CustomerDto customerDto = new CustomerDto();

		// Execute the method
		try {
			creditCardController.applyForCreditCard(customerDto);
		} catch (ValidationException e) {
			// Verify the expected exception
			assertEquals(ValidationException.class, e.getClass());
		}

		// Verify that the customerService.createCustomerAndApplicationRequests() method
		// was not called
		verify(customerService, never()).createCustomerAndApplicationRequests(getCustomer());
	}

	@Test
	public void testApplyForCreditCard_Success() {
		// Prepare test data
		CustomerDto customerDto = getCustomerDto();
		// Mock dependencies
		when(customerService.createCustomerAndApplicationRequests(customerDto.toCustomer()))
				.thenReturn(getCreditCardRequest());

		// Execute the method
		ResponseEntity<Object> response = creditCardController.applyForCreditCard(customerDto);

		// Verify the interactions and assertions
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testApplyForCreditCard_ApplicationNotRaised() {
		// Prepare test data
		CustomerDto customerDto = getCustomerDto();
		Customer customer = customerDto.toCustomer();

		// Mock dependencies
		when(customerService.createCustomerAndApplicationRequests(customerDto.toCustomer())).thenReturn(null);

		// Execute the method
		ResponseEntity<Object> response = creditCardController.applyForCreditCard(customerDto);

		// Verify the interactions and assertions
		verify(customerService, times(1)).createCustomerAndApplicationRequests(customer);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Application not raised.", response.getBody());
	}

	@Test
	public void testupdateCreditCardStatus_Success() {
		UUID applicationUuid = UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c");
		RecordStatus status = RecordStatus.APPROVED;
		String comment = "Approved";

		// Mock dependencies
		when(customerService.updateApplicationStatus(applicationUuid, status, comment)).thenReturn(true);
		ResponseEntity<Object> response = creditCardController.updateCreditCardStatus(getCreditCardRequestPutDto());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Successfully updated", response.getBody());
	}

	@Test
	public void testupdateCreditCardStatus_Failure() {
		UUID applicationUuid = UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c");
		RecordStatus status = RecordStatus.APPROVED;
		String comment = "Approved";

		// Mock dependencies
		when(customerService.updateApplicationStatus(applicationUuid, status, comment)).thenReturn(false);
		ResponseEntity<Object> response = creditCardController.updateCreditCardStatus(getCreditCardRequestPutDto());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Not updated", response.getBody());
	}

	private CreditCardRequest getCreditCardRequest() {
		CreditCardRequest creditCardRequest = new CreditCardRequest();
		creditCardRequest.setCustomerUuid(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));
		creditCardRequest.setStatus(RecordStatus.APPROVED);
		creditCardRequest.setVerificationComments("Created");
		creditCardRequest.setId(UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c"));
		creditCardRequest.setNotificationStatus(NotificationStatus.NEW);

		return creditCardRequest;
	}

	private CustomerDto getCustomerDto() {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setAddress("Address");
		customerDto.setCustomerName("John Doe");
		customerDto.setMobileNumber("1234567890");
		customerDto.setCommunicationInfo("Call to Mobile number");
		return customerDto;
	}

	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setAddress("Address");
		customer.setCommunicationInfo("Call to Mobile number");
		customer.setCustomerId("123");
		customer.setCustomerName("John Doe");
		customer.setMobileNumber("1234567890");
		customer.setId(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));
		return customer;
	}

	private CreditCardRequestPutDto getCreditCardRequestPutDto(){
		CreditCardRequestPutDto creditCardRequestPutDto = new CreditCardRequestPutDto();
		creditCardRequestPutDto.setComment("Approved");
		creditCardRequestPutDto.setStatus(RecordStatus.APPROVED);
		creditCardRequestPutDto.setApplicationUuid(UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c"));
		return creditCardRequestPutDto;
	}

}
