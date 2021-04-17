package com.makeitlouder.domain;

import com.makeitlouder.domain.security.Role;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 733233714059063212L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private boolean isVerified = false;

    private String emailVerificationToken;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservation;


    @Builder
    public User(Integer version, Timestamp createdDate, Timestamp lastModified, UUID id, String firstName,
                String lastName, String email, String encryptedPassword, boolean isVerified,
                String emailVerificationToken, Address address, Set<Role> roles) {
        super(version, createdDate, lastModified);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.isVerified = isVerified;
        this.emailVerificationToken = emailVerificationToken;
        this.address = address;
        this.roles = roles;
    }
}
