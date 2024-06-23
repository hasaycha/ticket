package com.hamdan.concert.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hamdan.concert.entity.Concert;
import com.hamdan.concert.entity.Customer;
import com.hamdan.concert.entity.Transaction;
import com.hamdan.concert.enums.MessageResponse;
import com.hamdan.concert.handler.exception.BusinessBadRequestException;
import com.hamdan.concert.handler.exception.BusinessNoContentException;
import com.hamdan.concert.handler.exception.BusinessNotFoundException;
import com.hamdan.concert.model.base.ResponseApi;
import com.hamdan.concert.model.request.BookingConcertRequest;
import com.hamdan.concert.model.request.CreateConcertRequest;
import com.hamdan.concert.model.response.BookingTicketConcertResponse;
import com.hamdan.concert.model.response.GetConcertResponse;
import com.hamdan.concert.repository.ConcertRepository;
import com.hamdan.concert.repository.CustomerRepository;
import com.hamdan.concert.repository.TransactionRepository;
import com.hamdan.concert.service.ConcertService;
import com.hamdan.concert.util.CustomUtil;
import com.hamdan.concert.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("concertService")
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    @Override
    public ResponseApi<List<GetConcertResponse>> getListAvailableConcert() {
        Time currentTime = Time.valueOf(LocalTime.now());

        List<Concert> concerts = concertRepository.findByAvailability(currentTime);

        if (CollectionUtils.isEmpty(concerts)) {
            throw new BusinessNoContentException(MessageResponse.NO_CONCERT_AVAILABLE);
        }

        List<GetConcertResponse> response = ObjectMapperUtil.convertValue(concerts, new TypeReference<List<GetConcertResponse>>() {
        });

        return new ResponseApi<>(response);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseApi<GetConcertResponse> getByCodeAndAvailabilityConcert(String code) {
        Time currentTime = Time.valueOf(LocalTime.now());

        Concert concert = concertRepository.findByCodeAndAvailability(code, currentTime)
                .orElseThrow(() -> new BusinessNotFoundException(MessageResponse.CONCERT_NOT_FOUND));

        GetConcertResponse response = ObjectMapperUtil.convertValue(concert, GetConcertResponse.class);

        return new ResponseApi<>(response);
    }

    @Transactional
    @Override
    public ResponseApi<BookingTicketConcertResponse> bookingConcert(String code, BookingConcertRequest bookingConcertRequest) {
        Time currentTime = Time.valueOf(LocalTime.now());

        Customer customer = customerRepository.findByEmail(bookingConcertRequest.getEmail())
                .orElseThrow(() -> new BusinessNotFoundException(MessageResponse.CUSTOMER_NOT_FOUND));

        Concert concert = concertRepository.findByCodeAndAvailability(code, currentTime, bookingConcertRequest.getQty())
                .orElseThrow(() -> new BusinessNotFoundException(MessageResponse.CONCERT_NOT_FOUND));

        Transaction trx = new Transaction();
        trx.setCustomer(customer);
        trx.setConcert(concert);
        trx.setPrice(concert.getPrice());
        trx.setQty(bookingConcertRequest.getQty());

        BigDecimal totalPrice = concert.getPrice().multiply(BigDecimal.valueOf(bookingConcertRequest.getQty()));
        trx.setTotalPrice(totalPrice);
        trx.setCode(CustomUtil.generateTransactionCode());

        concert.setQuota(concert.getQuota() - bookingConcertRequest.getQty());

        concertRepository.save(concert);
        transactionRepository.save(trx);

        return new ResponseApi<>(buildBookingTicketConcertResponse(trx));
    }

    @Transactional
    @Override
    public ResponseApi<GetConcertResponse> createConcert(CreateConcertRequest createConcertRequest) {
        String code = CustomUtil.generateConcertCode(createConcertRequest.getName());

        Optional<Concert> concertOpt = concertRepository.findByCode(code);
        if (concertOpt.isPresent()) {
            throw new BusinessBadRequestException(MessageResponse.CONCERT_EXIST);
        }

        Concert concert = Concert.builder()
                .name(createConcertRequest.getName())
                .code(code)
                .quota(createConcertRequest.getQuota())
                .price(createConcertRequest.getPrice())
                .timeFrom(Time.valueOf(createConcertRequest.getTimeFrom()))
                .timeTo(Time.valueOf(createConcertRequest.getTimeTo()))
                .build();

        concertRepository.save(concert);

        GetConcertResponse response = ObjectMapperUtil.convertValue(concert, GetConcertResponse.class);
        return new ResponseApi<>(response);
    }

    private BookingTicketConcertResponse buildBookingTicketConcertResponse(Transaction trx) {
        return BookingTicketConcertResponse.builder()
                .transactionCode(trx.getCode())
                .concertCode(trx.getConcert().getCode())
                .concertName(trx.getConcert().getName())
                .customerName(trx.getCustomer().getName())
                .customerEmail(trx.getCustomer().getEmail())
                .qty(trx.getQty())
                .totalPrice(trx.getTotalPrice())
                .build();
    }
}
