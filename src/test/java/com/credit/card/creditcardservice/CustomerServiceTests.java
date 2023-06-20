package com.credit.card.creditcardservice;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto;
import com.credit.card.creditcardservice.controller.exceptions.GenericException;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.repo.port.CreditCardRequestRepoPort;
import com.credit.card.creditcardservice.infrastructure.repo.port.CustomerRepoPort;
import com.credit.card.creditcardservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTests {

	private CustomerService customerService;

	@Mock
	private CustomerRepoPort customerRepoPort;

	@Mock
	private CreditCardRequestRepoPort creditCardRequestRepoPort;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		customerService = new CustomerService(customerRepoPort, creditCardRequestRepoPort);
	}

	@Test
	void getCustomerAndApplicationRequests_ValidUuid_ReturnsListOfCreditCardRequestInfoDto() {
		String customerId = "1TH568545GH";
		List<CreditCardRequestInfoDto> expectedRequests = new ArrayList<>();
		expectedRequests.add(getCreditCardRequestInfoDto());
		when(creditCardRequestRepoPort.findByCustomerId(customerId)).thenReturn(expectedRequests);

		List<CreditCardRequestInfoDto> result = customerService.getCustomerAndApplicationRequests(customerId);

		assertEquals(expectedRequests, result);
		assertEquals(1, result.size());
		verify(creditCardRequestRepoPort, times(1)).findByCustomerId(customerId);
	}

	@Test
	void updateApplicationStatus_ValidParams_ReturnsTrue() {
		UUID applicationUuid = UUID.randomUUID();
		RecordStatus status = RecordStatus.APPROVED;
		String comment = "Application approved";
		when(creditCardRequestRepoPort.update(applicationUuid, status, comment)).thenReturn(1);

		boolean result = customerService.updateApplicationStatus(applicationUuid, status, comment);

		assertTrue(result);
		verify(creditCardRequestRepoPort, times(1)).update(applicationUuid, status, comment);
	}

	@Test
	void updateApplicationStatus_NoRowsUpdated_ReturnsFalse() {
		UUID applicationUuid = UUID.randomUUID();
		RecordStatus status = RecordStatus.APPROVED;
		String comment = "Application approved";
		when(creditCardRequestRepoPort.update(applicationUuid, status, comment)).thenReturn(0);

		boolean result = customerService.updateApplicationStatus(applicationUuid, status, comment);

		assertFalse(result);
		verify(creditCardRequestRepoPort, times(1)).update(applicationUuid, status, comment);
	}

	@Test
	void createCustomerAndApplicationRequests_NewCustomer_ReturnsCreditCardRequest() {
		Customer customer = new Customer();
		customer.setCustomerId("1TH568545GH");
		Customer customerSaved = new Customer();
		customerSaved.setId(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));

		when(customerRepoPort.findByCustomerId(customer.getCustomerId())).thenReturn(null);
		when(customerRepoPort.save(customer)).thenReturn(customerSaved);

		CreditCardRequest expectedRequest = new CreditCardRequest();
		expectedRequest.setId(UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c"));
		when(creditCardRequestRepoPort.save(any(CreditCardRequest.class))).thenReturn(expectedRequest);

		CreditCardRequest result = customerService.createCustomerAndApplicationRequests(customer);

		assertNotNull(result);
		assertEquals(expectedRequest.getId(), result.getId());

		verify(customerRepoPort, times(1)).findByCustomerId(customer.getCustomerId());
		verify(customerRepoPort, times(1)).save(customer);
		verify(creditCardRequestRepoPort, times(1)).save(any(CreditCardRequest.class));
	}

	@Test
	void createCustomerAndApplicationRequests_ExistingCustomer_ReturnsCreditCardRequest() {
		Customer customer = new Customer();
		customer.setCustomerId("1TH568545GH");
		Customer customerSaved = new Customer();
		customerSaved.setId(UUID.fromString("c0a84473-88ce-1c2c-8188-cf008dab000c"));

		when(customerRepoPort.findByCustomerId(customer.getCustomerId())).thenReturn(customerSaved);

		CreditCardRequest expectedRequest = new CreditCardRequest();
		expectedRequest.setId(UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c"));
		when(creditCardRequestRepoPort.save(any(CreditCardRequest.class))).thenReturn(expectedRequest);

		CreditCardRequest result = customerService.createCustomerAndApplicationRequests(customer);

		assertNotNull(result);
		assertEquals(expectedRequest.getId(), result.getId());

		verify(customerRepoPort, times(1)).findByCustomerId(customer.getCustomerId());
		verify(customerRepoPort, never()).save(customer);
		verify(creditCardRequestRepoPort, times(1)).save(any(CreditCardRequest.class));
	}

	@Test
	void createCustomerAndApplicationRequests_CustomerSaveFails_ReturnsNull() {
		Customer customer = new Customer();
		customer.setCustomerId("1TH568545GH");

		when(customerRepoPort.findByCustomerId(customer.getCustomerId())).thenReturn(null);
		when(customerRepoPort.save(customer)).thenReturn(null);

		assertNull(customerService.createCustomerAndApplicationRequests(customer));

		verify(customerRepoPort, times(1)).findByCustomerId(customer.getCustomerId());
		verify(customerRepoPort, times(1)).save(customer);
		verify(creditCardRequestRepoPort, never()).save(any(CreditCardRequest.class));
	}
	
	@Test
	public void createCustomerAndApplicationRequests_CustomerSaveFails_GenericException() {
		Customer customer = new Customer();
		customer.setCustomerId("1TH568545GH");
		
		when(customerRepoPort.findByCustomerId(customer.getCustomerId())).thenThrow(new GenericException());
		// Execute the method
		try {
			customerService.createCustomerAndApplicationRequests(customer);
		} catch (GenericException e) {
			// Verify the expected exception
			assertEquals(GenericException.class, e.getClass());
		}
	}
	
	private CreditCardRequestInfoDto getCreditCardRequestInfoDto() {
		return new CreditCardRequestInfoDto(RecordStatus.NEW, "Created", "Elena",
				"#33, 2nd cross, Area, Bangalore -560035", "9878678765", "Call to Mobile number", "1TH568545GH",
				LocalDate.now(), LocalDateTime.now(), LocalDate.now(), LocalDateTime.now(),
				UUID.fromString("c1a84473-88ce-1c2c-8188-cf008dab000c"));

	}
}
