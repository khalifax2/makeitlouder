package com.makeitlouder.shared.dto;

import com.makeitlouder.domain.enumerated.Gender;
import com.makeitlouder.domain.enumerated.Status;
import com.makeitlouder.domain.enumerated.Type;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {
    private static final long serialVersionUID = -2408495889309927443L;

    private Long id;
    private String name;
    private Type petType;
    private Status petStatus;
    private Gender gender;
    private boolean imageLink;

}
