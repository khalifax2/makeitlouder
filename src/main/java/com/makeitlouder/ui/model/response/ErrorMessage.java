package com.makeitlouder.ui.model.response;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private Date timeStamp;
    private String message;
}
