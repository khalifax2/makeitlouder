package com.makeitlouder.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "pet")
public class Pet extends BaseEntity {

    private static final long serialVersionUID = 2004133378864529459L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String imagePath;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pet_status_id")
    private PetStatus petStatus;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gender")
    private Gender gender;


    @Builder
    public Pet(Integer version, Timestamp createdDate, Timestamp lastModified, Long id,
               String name, String imagePath, PetType petType, PetStatus petStatus,
               Gender gender) {
        super(version, createdDate, lastModified);
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.petType = petType;
        this.petStatus = petStatus;
        this.gender = gender;
    }
}
