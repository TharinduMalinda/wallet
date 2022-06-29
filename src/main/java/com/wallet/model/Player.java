package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Entity class for player.
 *
 * @author Malinda
 *
 */

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Entity
@Table(name = "PLAYER")
@DynamicInsert
@DynamicUpdate
public class Player {

    @Id
    @Column(name = "playerId")
    private Long playerId;

    private String firstName;

    private String lastName;

    private ZonedDateTime createdDate = ZonedDateTime.now();

    private ResourceStatus playerStatus;

    @OneToOne()
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

}
