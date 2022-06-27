package com.wallet.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
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
/*  used unidi mapping for the tranaction table, since this is high volume table and to maintain the speed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

 */
    @Digits(integer = 15, fraction = 0, message = "Invalid account Id")
    private Long accountId;

    @NotNull
    private TransactionTypes txnType;

    @Digits(integer=4, fraction=4 ,message = "Invalid balanace")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1000.0")
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
