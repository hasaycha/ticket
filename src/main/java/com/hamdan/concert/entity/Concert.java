package com.hamdan.concert.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hamdan.concert.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.sql.Time;

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

    @Column(nullable = false, columnDefinition = "Decimal(14,2) default '0.00'")
    @Builder.Default
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "time_from", nullable = false)
    private Time timeFrom;

    @Column(name = "time_to", nullable = false)
    private Time timeTo;
}
