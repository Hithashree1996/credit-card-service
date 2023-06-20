package com.credit.card.creditcardservice.infrastructure.repo.port;

import com.credit.card.creditcardservice.domain.Customer;

/**
 * Interface for the Customer Repository Port.
 * Provides methods for managing customers.
 */
public interface CustomerRepoPort {
	
	Customer save(Customer customer);
	
	Customer findByCustomerId(String customerId);

}
