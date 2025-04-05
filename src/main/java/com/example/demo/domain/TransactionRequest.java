package com.example.demo.domain;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@Data
public class TransactionRequest {
    private String id;

    @NotNull
    private BigDecimal amount;
    @NotBlank
    @Pattern(regexp = "^(XF|TH)$", message = "Invalid transaction type")
    private String type;
    @NotNull
    private String userId;
}
