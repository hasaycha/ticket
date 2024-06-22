package com.hamdan.concert.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.entity.base.BaseEntity;
import com.hamdan.concert.enums.TicketStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2370070804162253849L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(nullable = false)
    private Integer qty;

    @Column(name = "total_price", nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    @Builder.Default
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private Set<TicketDetail> ticketDetails;
}
