package com.hamdan.concert.service;

import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.CustomerRegistrationRequest;
import com.hamdan.concert.model.response.GetCustomerResponse;

public interface CustomerService {
    ResponseApi<GetCustomerResponse> customerRegistration(CustomerRegistrationRequest customerRegistrationRequest);

    ResponseApi<GetCustomerResponse> getCustomerByEmail(String email);
}
