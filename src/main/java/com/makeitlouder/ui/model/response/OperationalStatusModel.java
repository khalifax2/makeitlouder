package com.makeitlouder.ui.model.response;

import lombok.*;

import javax.persistence.Access;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationalStatusModel {
    private String operationName;
    private String operationResult;
}
