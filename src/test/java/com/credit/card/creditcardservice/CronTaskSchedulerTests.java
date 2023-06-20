package com.credit.card.creditcardservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.enums.NotificationStatus;
import com.credit.card.creditcardservice.enums.RecordStatus;
import com.credit.card.creditcardservice.infrastructure.repo.port.CreditCardRequestRepoPort;
import com.credit.card.creditcardservice.task.CronTaskScheduler;

public class CronTaskSchedulerTests {

	@Mock
	private CreditCardRequestRepoPort creditCardRequestRepoPort;

	private CronTaskScheduler cronTaskScheduler;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		cronTaskScheduler = new CronTaskScheduler(creditCardRequestRepoPort);
	}

	@Test
	void notifyCustomerStatus_NoCreditCardRequests() {
		List<CreditCardRequest> emptyList = new ArrayList<>();
		when(creditCardRequestRepoPort.findByNotificationStatus(NotificationStatus.NEW)).thenReturn(emptyList);

		List<CreditCardRequest> result = cronTaskScheduler.notifyCustomerStatus();

		verify(creditCardRequestRepoPort, times(1)).findByNotificationStatus(NotificationStatus.NEW);
		assertEquals(emptyList, result);
	}

	@Test
	void notifyCustomerStatus_SuccessfulNotification() {
		CreditCardRequest request1 = getCreditCardRequest();
		CreditCardRequest request2 = getCreditCardRequest();
		List<CreditCardRequest> requestList = new ArrayList<>();
		requestList.add(request1);
		requestList.add(request2);

		when(creditCardRequestRepoPort.findByNotificationStatus(NotificationStatus.NEW)).thenReturn(requestList);

		List<CreditCardRequest> result = cronTaskScheduler.notifyCustomerStatus();

		verify(creditCardRequestRepoPort, times(1)).findByNotificationStatus(NotificationStatus.NEW);

		assertEquals(NotificationStatus.SUCCESS, request1.getNotificationStatus());
		assertEquals(NotificationStatus.SUCCESS, request2.getNotificationStatus());
		assertEquals(requestList, result);
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
}
