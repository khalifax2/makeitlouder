package com.makeitlouder.shared.dto;

import com.makeitlouder.domain.PetStatus;
import com.makeitlouder.domain.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetDto extends BaseDto {
    private static final long serialVersionUID = -7627877486431054947L;

    private String name;
    private String imagePath;
    private PetType petType;
    private PetStatus petStatus;
}
