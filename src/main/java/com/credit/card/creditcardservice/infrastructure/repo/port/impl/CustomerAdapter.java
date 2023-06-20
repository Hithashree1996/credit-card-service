package com.credit.card.creditcardservice.infrastructure.repo.port.impl;

import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.infrastructure.entity.CustomerEntity;
import com.credit.card.creditcardservice.infrastructure.repo.CustomerRepository;
import com.credit.card.creditcardservice.infrastructure.repo.port.CustomerRepoPort;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Customer Repository Port.
 * Provides methods for managing customers using the CustomerRepository.
 */

@Component
public class CustomerAdapter implements CustomerRepoPort {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer save(Customer customer) {
		CustomerEntity customerDb = new CustomerEntity().getCustomer(customer);
		customerDb.setCustomerId(RandomStringUtils.randomAlphanumeric(12).toUpperCase());
		customerRepository.save(customerDb);
		return customerDb.getCustomerDomain();
	}

	@Override
	public Customer findByCustomerId(String customerId) {
		CustomerEntity customerDb = customerRepository.findByCustomerId(customerId);
		if (customerDb != null) {
			return customerDb.getCustomerDomain();
		}
		return null;
	}

}
