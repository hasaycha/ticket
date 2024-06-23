package com.hamdan.concert.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRegistrationRequest {

    @NotBlank
    private String email;

    private String name;
}
