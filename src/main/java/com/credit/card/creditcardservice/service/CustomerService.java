package com.credit.card.creditcardservice.service;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto;
import com.credit.card.creditcardservice.controller.exceptions.GenericException;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.repo.port.CreditCardRequestRepoPort;
import com.credit.card.creditcardservice.infrastructure.repo.port.CustomerRepoPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing customers and credit card applications.
 */

@Service
public class CustomerService {

	private CustomerRepoPort customerRepoPort;

	private CreditCardRequestRepoPort creditCardRequestRepoPort;

	/**
	 * Constructs a new CustomerService with the specified repositories.
	 *
	 * @param customerRepoPort          the customer repository port
	 * @param creditCardRequestRepoPort the credit card request repository port
	 */
	public CustomerService(CustomerRepoPort customerRepoPort, CreditCardRequestRepoPort creditCardRequestRepoPort) {
		super();
		this.customerRepoPort = customerRepoPort;
		this.creditCardRequestRepoPort = creditCardRequestRepoPort;
	}

	/**
	 * Retrieves the customer and their associated credit card application requests.
	 *
	 * @param customerUuid the UUID of the customer
	 * @return a list of CreditCardRequestInfoDto objects containing the customer's application requests
	 */
	public List<CreditCardRequestInfoDto> getCustomerAndApplicationRequests(String customerUuid) {
		return creditCardRequestRepoPort.findByCustomerId(customerUuid);
	}

	/**
	 * Updates the status of a credit card application.
	 *
	 * @param applicationUuid the UUID of the credit card application
	 * @param status          the new status to set
	 * @param comment         the comment to associate with the update
	 * @return true if the update was successful, false otherwise
	 */
	public boolean updateApplicationStatus(UUID applicationUuid, RecordStatus status, String comment) {
		int colsUpdated = creditCardRequestRepoPort.update(applicationUuid, status, comment);
		return colsUpdated > 0;
	}

	/**
	 * Creates a new customer and their associated credit card application requests.
	 *
	 * @param customer the customer to create
	 * @return the created CreditCardRequest object
	 * @throws GenericException if an error occurs while creating the customer and requests
	 */
	@Transactional
	public CreditCardRequest createCustomerAndApplicationRequests(Customer customer) {
		try {
			Customer customerSaved = customerRepoPort.findByCustomerId(customer.getCustomerId());
			if (customerSaved == null) {
				customerSaved = customerRepoPort.save(customer);
			}
			if (customerSaved != null) {
				return saveCreditCardRequest(customerSaved.getId(),customerSaved.getCustomerId());
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException();
		}
	}

	/**
	 * Saves a new credit card request with the given customer UUID.
	 *
	 * @param customerUuid the UUID of the customer
	 * @return the created CreditCardRequest object
	 */
	private CreditCardRequest saveCreditCardRequest(UUID customerUuid,String customerId) {
		CreditCardRequest creditCardRequest = new CreditCardRequest();
		creditCardRequest.setCustomerUuid(customerUuid);
		creditCardRequest.setVerificationComments("Credit Card Request Raised");
		creditCardRequest.setCustomerId(customerId);
		return creditCardRequestRepoPort.save(creditCardRequest);
	}

}
