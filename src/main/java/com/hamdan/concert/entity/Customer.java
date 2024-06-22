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
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1806306537901710408L;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private Set<Ticket> tickets;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 100)
    private String name;

}
