package com.makeitlouder.domain;

import com.makeitlouder.domain.enumerated.Gender;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.domain.enumerated.Type;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @Enumerated(EnumType.STRING)
    private Type petType;

    @Enumerated(EnumType.STRING)
    private Status petStatus;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    @Builder
    public Pet(Integer version, Timestamp createdDate, Timestamp lastModified, Long id,
               String name, String imagePath, Type petType, Status petStatus, Gender gender) {
        super(version, createdDate, lastModified);
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.petType = petType;
        this.petStatus = petStatus;
        this.gender = gender;
    }
}
