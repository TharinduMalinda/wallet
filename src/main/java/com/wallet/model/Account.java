package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Entity class for account.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "account")
@DynamicInsert
@DynamicUpdate
public class Account {
    @Id
    @Digits(integer = 15, fraction = 0, message = "Invalid account Id")
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @NotNull
    private ResourceStatus accountStatus;

    @Digits(integer = 15,fraction = 4 ,message = "Invalid balanace")
    private BigDecimal balance;

    @NotBlank
    private String currency;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdDate = ZonedDateTime.now();
}
