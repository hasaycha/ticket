package com.hamdan.concert.service.impl;

import com.hamdan.concert.entity.Customer;
import com.hamdan.concert.enums.MessageResponse;
import com.hamdan.concert.handler.exception.BusinessBadRequestException;
import com.hamdan.concert.handler.exception.BusinessNotFoundException;
import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.CustomerRegistrationRequest;
import com.hamdan.concert.model.response.GetCustomerResponse;
import com.hamdan.concert.repository.CustomerRepository;
import com.hamdan.concert.service.CustomerService;
import com.hamdan.concert.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;


    @Transactional
    @Override
    public ResponseApi<GetCustomerResponse> customerRegistration(CustomerRegistrationRequest customerRegistrationRequest) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(customerRegistrationRequest.getEmail());
        if (customerOpt.isPresent()) {
            throw new BusinessBadRequestException(MessageResponse.CUSTOMER_EXIST);
        }

        Customer customer = Customer.builder()
                .email(customerRegistrationRequest.getEmail())
                .name(Strings.isNotBlank(customerRegistrationRequest.getName()) ? customerRegistrationRequest.getName() : null)
                .build();

        customerRepository.save(customer);

        GetCustomerResponse response = ObjectMapperUtil.convertValue(customer, GetCustomerResponse.class);
        return new ResponseApi<>(response);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApi<GetCustomerResponse> getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessNotFoundException(MessageResponse.CUSTOMER_NOT_FOUND));

        GetCustomerResponse response = ObjectMapperUtil.convertValue(customer, GetCustomerResponse.class);
        return new ResponseApi<>(response);
    }
}
