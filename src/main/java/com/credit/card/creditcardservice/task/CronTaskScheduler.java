package com.credit.card.creditcardservice.task;

import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.infrastructure.repo.port.CreditCardRequestRepoPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component class for scheduling cron tasks related to credit card requests.
 */
@Component
public class CronTaskScheduler {

	private CreditCardRequestRepoPort creditCardRequestRepoPort;

	/**
	 * Constructs a new CronTaskScheduler with the specified credit card request repository port.
	 *
	 * @param creditCardRequestRepoPort the credit card request repository port
	 */
	public CronTaskScheduler(CreditCardRequestRepoPort creditCardRequestRepoPort) {
		super();
		this.creditCardRequestRepoPort = creditCardRequestRepoPort;
	}

	/**
	 * Scheduled method to notify customers about the status of their credit card requests.
	 *
	 * @return a list of CreditCardRequest objects that were processed during the notification
	 */
	@Scheduled(cron = "${cron.job.notify}")
	public List<CreditCardRequest> notifyCustomerStatus() {
		List<CreditCardRequest> creditCardRequestList = creditCardRequestRepoPort
				.findByNotificationStatus(NotificationStatus.NEW);
		creditCardRequestList.forEach(creditCardRequest -> {
			try {
				// Notify
				creditCardRequest.setNotificationStatus(NotificationStatus.SUCCESS);
			} catch (Exception e) {
				creditCardRequest.setNotificationStatus(NotificationStatus.FAILED);
			}
			creditCardRequestRepoPort.updateNotificationStatus(creditCardRequest.getId(),
					creditCardRequest.getNotificationStatus());
		});
		return creditCardRequestList;
	}

}
