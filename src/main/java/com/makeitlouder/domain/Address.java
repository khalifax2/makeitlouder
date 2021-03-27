package com.makeitlouder.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false, length = 120)
    private String street;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 20)
    private String postalCode;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
