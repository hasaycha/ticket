package com.hamdan.concert;

import com.hamdan.concert.entity.Concert;
import com.hamdan.concert.entity.Customer;
import com.hamdan.concert.entity.Transaction;
import com.hamdan.concert.model.request.BookingConcertRequest;
import com.hamdan.concert.repository.ConcertRepository;
import com.hamdan.concert.repository.CustomerRepository;
import com.hamdan.concert.repository.TransactionRepository;
import com.hamdan.concert.service.impl.ConcertServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ConcertTest {
    @InjectMocks
    ConcertServiceImpl service;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllAvailableConcert() {
        Time currentTime = Time.valueOf(LocalTime.now());

        List<Concert> list = new ArrayList<>();
        Concert concertOne = Concert.builder()
                .code("CT001")
                .name("Concert 1")
                .price(new BigDecimal(100000))
                .quota(1000)
                .timeFrom(Time.valueOf("10:00:00"))
                .timeTo(Time.valueOf("10:30:00"))
                .build();

        Concert concertTwo = Concert.builder()
                .code("CT002")
                .name("Concert 2")
                .price(new BigDecimal(200000))
                .quota(2000)
                .timeFrom(Time.valueOf("11:00:00"))
                .timeTo(Time.valueOf("11:30:00"))
                .build();

        Concert concertThree = Concert.builder()
                .code("CT003")
                .name("Concert 3")
                .price(new BigDecimal(300000))
                .quota(3000)
                .timeFrom(Time.valueOf("13:00:00"))
                .timeTo(Time.valueOf("13:30:00"))
                .build();

        list.add(concertOne);
        list.add(concertTwo);
        list.add(concertThree);

        Mockito.when(concertRepository.findByAvailability(currentTime)).thenReturn(list);

        var concertList = service.getListAvailableConcert();

        Assertions.assertEquals(3, concertList.getData().size());
        Mockito.verify(concertRepository, Mockito.times(1)).findByAvailability(currentTime);
    }

    @Test
    void shouldGetAvailableConcertByCode() {
        Time currentTime = Time.valueOf(LocalTime.now());
        String code = "CT001";

        Concert concert = Concert.builder()
                .code("CT001")
                .name("Concert 1")
                .price(new BigDecimal(100000))
                .quota(1000)
                .timeFrom(Time.valueOf("10:00:00"))
                .timeTo(Time.valueOf("10:30:00"))
                .build();

        Mockito.when(concertRepository.findByCodeAndAvailability(code, currentTime)).thenReturn(Optional.ofNullable(concert));

        final var customerDto = service.getByCodeAndAvailabilityConcert(code);

        Assertions.assertNotNull(customerDto);
        Assertions.assertEquals(customerDto.getData().getCode(), code);

        Mockito.verify(concertRepository, Mockito.times(1)).findByCodeAndAvailability(code, currentTime);
    }

    @Test
    void shouldCanBookingConcert() {
        String email = "hamdanrijali@gmail.com";
        String name = "Hamdan";
        Customer customer = Customer.builder()
                .name(name)
                .email(email)
                .build();

        Mockito.when(customerRepository.findByEmail(email)).thenReturn(Optional.ofNullable(customer));

        Time currentTime = Time.valueOf(LocalTime.now());
        String code = "CT001";
        int qty = 1;
        String trxCode = "CT-062320240719090190015803686";

        Concert concert = Concert.builder()
                .code("CT001")
                .name("Concert 1")
                .price(new BigDecimal(100000))
                .quota(1000)
                .timeFrom(Time.valueOf("10:00:00"))
                .timeTo(Time.valueOf("10:30:00"))
                .build();

        Transaction trx = new Transaction();
        trx.setCode(trxCode);
        trx.setQty(qty);
        trx.setCustomer(customer);
        trx.setConcert(concert);

        Mockito.when(concertRepository.findByCodeAndAvailability(code, currentTime, qty)).thenReturn(Optional.ofNullable(concert));

        Mockito.when(transactionRepository.save(trx)).thenReturn(trx);

        BookingConcertRequest bookingConcertRequest = new BookingConcertRequest();
        bookingConcertRequest.setEmail(email);
        bookingConcertRequest.setQty(qty);

        final var bookingConcert = service.bookingConcert(code, bookingConcertRequest);

        Assertions.assertNotNull(bookingConcert);
    }
}
