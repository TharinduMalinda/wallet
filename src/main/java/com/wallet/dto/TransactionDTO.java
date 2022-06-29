package com.wallet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wallet.model.TransactionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * This DTO is used to transferring transaction destails as a response.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
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
