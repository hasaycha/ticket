package com.hamdan.concert.controller;

import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.CustomerRegistrationRequest;
import com.hamdan.concert.model.response.GetCustomerResponse;
import com.hamdan.concert.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Customer Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success register a customer"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseApi<GetCustomerResponse> customerRegistration(@Valid @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        return customerService.customerRegistration(customerRegistrationRequest);
    }

    @Operation(summary = "Get customer by Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success get customer by email"),
            @ApiResponse(responseCode = "400", description = "Invalid data request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
    })
    @GetMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public ResponseApi<GetCustomerResponse> getCustomerByEmail(@Valid @NotBlank @RequestParam() String email) {
        return customerService.getCustomerByEmail(email);
    }
}
