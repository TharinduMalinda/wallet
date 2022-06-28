package com.wallet.dto;

import com.wallet.model.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor

public class TransactionRequestDTO {

    @NotBlank
    private String txnId;

    @Digits(integer = 15, fraction = 0, message = "Invalid account ID")
    private Long accountId;

    @NotNull
    private TransactionTypes txnType;

    @Digits(integer=4, fraction=4 ,message = "Invalid balanace")
    @DecimalMin(value = "0.1")
    @DecimalMax(value = "1000.0")
    private BigDecimal txnAmount;


}
