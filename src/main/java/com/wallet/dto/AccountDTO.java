package com.wallet.dto;

import com.wallet.model.ResourceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Builder

public class AccountDTO {

    private Long accountId;
    private ResourceStatus accountStatus;
    private BigDecimal balance;
    private String currency;

}
