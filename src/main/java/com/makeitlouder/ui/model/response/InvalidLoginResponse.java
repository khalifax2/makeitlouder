package com.makeitlouder.ui.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String message;

    public InvalidLoginResponse() {
        message = "Invalid email or password";
    }
}
