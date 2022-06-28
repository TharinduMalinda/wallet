package com.wallet.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity

public class Transaction  {
    @Id
    private String txnId;
/*  no mapping used for the tranaction table, since this is high volume table and to maintain the speed
 */
    private Long accountId;

    @NotNull
    private TransactionTypes txnType;

    private BigDecimal txnAmount;

    @Digits(integer=15, fraction=4 ,message = "Invalid balanace")
    private BigDecimal startBalance;

    @Digits(integer=15, fraction=4 ,message = "Invalid balanace")
    private BigDecimal endBalance;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime txnDateTime;

//    // Added the below to prevent JPA update the same Txn id
//    @Override
//    public String getId() {
//        return txnId;
//    }
//
//    @Override
//    public boolean isNew() {
//        return true;
//    }
}
