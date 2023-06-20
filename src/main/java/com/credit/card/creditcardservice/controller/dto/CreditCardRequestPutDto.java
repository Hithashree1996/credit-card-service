package com.credit.card.creditcardservice.controller.dto;

import com.credit.card.creditcardservice.enums.RecordStatus;
import lombok.Data;

import java.util.UUID;
@Data
public class CreditCardRequestPutDto {
    private UUID applicationUuid;
    private RecordStatus status;
    private String comment;
}
