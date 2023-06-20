package com.credit.card.creditcardservice.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Represents a base entity in the system.
 * Provides common fields and functionality for entities.
 */
@Data
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@Parameter(name = "uuid_gen_strategy_class", value = "org.hibernate.id.uuid.CustomVersionOneStrategy") })
	@Column(name = "id", updatable = false, nullable = false)
	protected UUID id;

	@Column(name = "created_date",columnDefinition = "DATE")
	private LocalDate createdDate = LocalDate.now();

	@Column(name = "created_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdTime = LocalDateTime.now();

}
