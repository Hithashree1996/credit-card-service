package com.credit.card.creditcardservice.infrastructure.repo;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.entity.CreditCardRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing credit card requests in the database.
 * Extends JpaRepository for basic CRUD operations on CreditCardRequestEntity objects.
 */

@Repository
public interface CreditCardRequestRepo extends JpaRepository<CreditCardRequestEntity, UUID> {

	/**
	 * Retrieves a list of CreditCardRequestInfoDto objects based on the given customer UUID.
	 *
	 * @param customerId the ID of the customer
	 * @return a list of CreditCardRequestInfoDto objects
	 */
	@Query("select new com.credit.card.creditcardservice.controller.dto.CreditCardRequestInfoDto(cc.status, cc.verificationComments, c.customerName,"
			+ "c.address, c.mobileNumber, c.communicationInfo, c.customerId, cc.createdDate,cc.createdTime, cc.updatedDate, cc.updatedTime,cc.id)"
			+ " from CreditCardRequestEntity cc inner join CustomerEntity c on cc.customerUuid = c.id where c.customerId = :customerId")
	List<CreditCardRequestInfoDto> findByCustomerId(String customerId);

	/**
	 * Retrieves a list of CreditCardRequestEntity objects based on the given notification status.
	 *
	 * @param notificationStatus the notification status
	 * @return a list of CreditCardRequestEntity objects
	 */
	List<CreditCardRequestEntity> findByNotificationStatus(NotificationStatus notificationStatus);

	/**
	 * Updates the status, verification comments, notification status, and update date/time of a CreditCardRequestEntity
	 * with the given ID.
	 *
	 * @param status               the new record status
	 * @param verificationComments the new verification comments
	 * @param notificationStatus   the new notification status
	 * @param id                   the ID of the CreditCardRequestEntity
	 * @param updatedDate          the updated date
	 * @param updatedTime          the updated time
	 * @return the number of affected rows
	 */
	@Modifying
	@Query("update CreditCardRequestEntity c set c.status = :status , c.verificationComments=:verificationComments, "
			+ "c.notificationStatus=:notificationStatus, c.updatedDate =:updatedDate, c.updatedTime =:updatedTime where c.id = :id")
	int updateStatusAndComment(@Param("status") RecordStatus status,
			@Param("verificationComments") String verificationComments,
			@Param("notificationStatus") NotificationStatus notificationStatus, @Param("id") UUID id,
			@Param("updatedDate") LocalDate updatedDate, @Param("updatedTime") LocalDateTime updatedTime);

	/**
	 * Updates the notification status, updated date, and updated time of a CreditCardRequestEntity with the given ID.
	 *
	 * @param notificationStatus the new notification status
	 * @param id                 the ID of the CreditCardRequestEntity
	 * @param updatedDate        the updated date
	 * @param updatedTime        the updated time
	 * @return the number of affected rows
	 */
	@Modifying
	@Query("update CreditCardRequestEntity c set c.notificationStatus = :notificationStatus, c.updatedDate = :updatedDate, "
			+ "c.updatedTime = :updatedTime where c.id = :id")
	int updateNotificationStatus(@Param("notificationStatus") NotificationStatus notificationStatus,
			@Param("id") UUID id, @Param("updatedDate") LocalDate updatedDate,
			@Param("updatedTime") LocalDateTime updatedTime);

}
