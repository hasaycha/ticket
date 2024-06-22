package com.hamdan.concert.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.entity.base.BaseEntity;
import com.hamdan.concert.enums.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;

@Entity
@Table(name = "ticket_detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDetail extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -5995049573839704317L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ticket ticket;

    @Column(name = "ticket_number", nullable = false)
    private String ticketNumber;

    @Column(name = "price", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
