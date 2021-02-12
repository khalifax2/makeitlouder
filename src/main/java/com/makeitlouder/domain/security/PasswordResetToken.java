package com.makeitlouder.domain.security;

import com.makeitlouder.domain.BaseEntity;
import com.makeitlouder.domain.User;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
