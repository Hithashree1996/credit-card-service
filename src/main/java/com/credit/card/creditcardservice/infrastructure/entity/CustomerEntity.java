package com.credit.card.creditcardservice.infrastructure.entity;

import com.credit.card.creditcardservice.domain.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Represents an entity for customers.
 * Extends the BaseEntity class and provides additional fields and methods for customer data.
 */

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerEntity extends BaseEntity {

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "address")
	private String address;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "communication_info")
	private String communicationInfo;

	@Column(name = "customer_id", unique = true)
	private String customerId;

	/**
	 * Retrieves the Customer domain object based on the data from this CustomerEntity instance.
	 *
	 * @return the Customer object
	 */
	public Customer getCustomerDomain() {
		Customer customer = new Customer();
		customer.setAddress(this.address);
		customer.setCommunicationInfo(this.communicationInfo);
		customer.setCustomerId(this.customerId);
		customer.setCustomerName(this.customerName);
		customer.setMobileNumber(this.mobileNumber);
		customer.setId(super.id);
		return customer;
	}

	/**
	 * Updates this CustomerEntity instance with data from the given Customer object.
	 *
	 * @param customer the Customer object with updated data
	 * @return this CustomerEntity instance
	 */
	public CustomerEntity getCustomer(Customer customer) {
		this.address = customer.getAddress();
		this.communicationInfo = customer.getCommunicationInfo();
		this.customerId = customer.getCustomerId();
		this.customerName = customer.getCustomerName();
		this.mobileNumber = customer.getMobileNumber();
		return this;
	}

}
