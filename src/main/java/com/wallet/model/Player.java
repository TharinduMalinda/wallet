package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor

@Entity
@Table(name = "PLAYER")
@DynamicInsert
@DynamicUpdate

public class Player {

    @Id
    @Digits(integer = 15, fraction = 0, message = "Invalid Player ID")
    @Column(name = "playerId")
    private Long playerId;

    @Length(min = 1, max = 30, message = "Lenth of first name should be in between 1,30")
    private String firstName;

    @Length(min = 1, max = 30, message = "Lenth of Last name should be in between 1,30")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @NotNull
    private ResourceStatus playerStatus;

    @OneToOne()
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;




}
