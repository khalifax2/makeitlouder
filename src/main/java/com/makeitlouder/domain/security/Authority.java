package com.makeitlouder.domain.security;

import com.makeitlouder.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;

}
