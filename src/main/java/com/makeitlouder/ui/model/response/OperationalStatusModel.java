package com.makeitlouder.ui.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class OperationalStatusModel {
    private String operationName;
    private String operationResult;
}
