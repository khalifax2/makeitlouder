package com.makeitlouder.domain.security;

import com.makeitlouder.domain.BaseEntity;
import com.makeitlouder.domain.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> user;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id")
    )
    private Set<Authority> authorities;

}
