package com.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.model.Transaction;
import com.wallet.model.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")

public class TransactionDTO {

    private String txnId;
    private Long accountId;
    private TransactionTypes txnType;
    private BigDecimal txnAmount;
    private BigDecimal startBalance;
    private BigDecimal endBalance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private ZonedDateTime txnDateTime;


}
