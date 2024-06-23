package com.hamdan.concert;

import com.hamdan.concert.entity.Customer;
import com.hamdan.concert.handler.exception.BusinessNotFoundException;
import com.hamdan.concert.model.request.CustomerRegistrationRequest;
import com.hamdan.concert.repository.CustomerRepository;
import com.hamdan.concert.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Objects;
import java.util.Optional;

class CustomerTest {
    @InjectMocks
    CustomerServiceImpl service;

    @Mock
    CustomerRepository dao;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateNewUser() {
        String name = "Hamdan";
        String email = "hamdanrijali@gmail.com";
        Mockito.when(dao.saveAndFlush(Mockito.any()))
                .thenAnswer(invocation -> {
                    Customer customer = invocation.getArgument(0);
                    assert Objects.equals(customer.getEmail(), email);
                    assert Objects.equals(customer.getName(), name);
                    return customer;
                });
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest();
        customerRegistrationRequest.setName(name);
        customerRegistrationRequest.setEmail(email);
        final var customerDto = service.customerRegistration(customerRegistrationRequest);

        Assertions.assertNotNull(customerDto);
        Assertions.assertEquals(customerDto.getData().getEmail(), email);
        Assertions.assertEquals(customerDto.getData().getName(), name);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        Assertions.assertThrows(
                BusinessNotFoundException.class,
                () -> service.getCustomerByEmail("dan@gmail.com")
        );
    }

    @Test
    void shouldGetCustomerByEmail() {
        String email = "hamdanrijali@gmail.com";
        String name = "Hamdan";
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .build();

        Mockito.when(dao.findByEmail(email)).thenReturn(Optional.ofNullable(customer));

        final var customerDto = service.getCustomerByEmail(email);

        Assertions.assertNotNull(customerDto);
        Assertions.assertEquals(customerDto.getData().getEmail(), email);
        Assertions.assertEquals(customerDto.getData().getName(), name);

        Mockito.verify(dao, Mockito.times(1)).findByEmail(email);
    }
}
