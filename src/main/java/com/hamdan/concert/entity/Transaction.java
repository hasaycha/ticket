package com.hamdan.concert.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.entity.base.BaseEntity;
import com.hamdan.concert.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -3386220737544258080L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ticket ticket;

    @Column(name = "transaction_number", nullable = false)
    private String transactionNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
