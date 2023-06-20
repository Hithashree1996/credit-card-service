package com.credit.card.creditcardservice.infrastructure.repo;

import com.credit.card.creditcardservice.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for managing customers in the database.
 * Extends JpaRepository for basic CRUD operations on CustomerEntity objects.
 */

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID>{

	/**
	 * Retrieves a CustomerEntity object based on the given customer ID.
	 *
	 * @param customerId the customer ID
	 * @return the CustomerEntity object
	 */
	CustomerEntity findByCustomerId(String customerId);
	
}
