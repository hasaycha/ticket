package com.hamdan.concert.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.entity.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Concert extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4880647938647861047L;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false)
    private Integer quota;

    @Column(nullable = false, columnDefinition = "Decimal(10,2) default '0.00'")
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "all_time", nullable = false)
    @Builder.Default
    private Boolean allTime = true;

    @Column(name = "time_from")
    private Time timeFrom;

    @Column(name = "time_to")
    private Time timeTo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "concert_id")
    private Set<Ticket> tickets;
}
