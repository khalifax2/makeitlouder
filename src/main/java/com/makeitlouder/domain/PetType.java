package com.makeitlouder.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.TypeAlias;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pet_type")
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

}
