package com.sistema.gestion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MfaRequest {
    private String username;
    private String otp;
}
