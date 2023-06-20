package com.credit.card.creditcardservice.controller;

import com.credit.card.creditcardservice.controller.dto.CreditCardRequestPutDto;
import com.credit.card.creditcardservice.controller.dto.CustomerDto;
import com.credit.card.creditcardservice.controller.exceptions.ValidationException;
import com.credit.card.creditcardservice.domain.CreditCardRequest;
import com.credit.card.creditcardservice.domain.Customer;
import com.credit.card.creditcardservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * The CreditCardController class handles HTTP requests related to credit card
 * operations. It provides endpoints for applying for a credit card and
 * retrieving credit card information.
 *
 * @author Hitha
 * @since 2023-06-19
 */
@RestController
@RequestMapping("/customers/credit-cards")
public class CreditCardController {

    private CustomerService customerService;

    public CreditCardController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    /**
     * Apply for a credit card with the provided customer information.
     *
     * @param customerDto The DTO containing customer information.
     * @return A ResponseEntity with the result of the application.
     * @throws ValidationException If the provided customer information is invalid.
     */
    @PostMapping
    public ResponseEntity<Object> applyForCreditCard(@RequestBody CustomerDto customerDto) {
        if (!StringUtils.hasLength(customerDto.getAddress()) || !StringUtils.hasLength(customerDto.getCustomerName())
                || !StringUtils.hasLength(customerDto.getMobileNumber())) {
            throw new ValidationException();
        }
        Customer customer = customerDto.toCustomer();
        CreditCardRequest creditCardRequest = customerService.createCustomerAndApplicationRequests(customer);
        if (creditCardRequest != null) {
            return new ResponseEntity<>(creditCardRequest, HttpStatus.OK);
        }
        return new ResponseEntity<>("Application not raised.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Retrieve credit card applications information for the specified customer.
     *
     * @param customerId The ID of the customer to retrieve information.
     * @return A ResponseEntity with the credit card information.
     */
    @GetMapping
    public ResponseEntity<Object> getCreditCardStatus(@RequestParam(name = "customerId") String customerId) {
        return new ResponseEntity<>(customerService.getCustomerAndApplicationRequests(customerId), HttpStatus.OK);
    }

    /**
     * Update the status and comment of a credit card application.
     *
     * @param creditCardRequestPutDto The DTO containing updated information.
     * @return A ResponseEntity indicating the result of the update operation.
     */
    @PutMapping
    public ResponseEntity<Object> updateCreditCardStatus(@RequestBody CreditCardRequestPutDto creditCardRequestPutDto) {
        boolean success = customerService.updateApplicationStatus(creditCardRequestPutDto.getApplicationUuid(),
                creditCardRequestPutDto.getStatus(), creditCardRequestPutDto.getComment());
        if (success) {
            return new ResponseEntity<>("Successfully updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not updated", HttpStatus.BAD_REQUEST);
    }
}
