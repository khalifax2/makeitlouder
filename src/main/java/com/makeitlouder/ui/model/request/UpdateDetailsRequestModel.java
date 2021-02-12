package com.makeitlouder.ui.model.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateDetailsRequestModel {
    private String firstName;
    private String lastName;
}
