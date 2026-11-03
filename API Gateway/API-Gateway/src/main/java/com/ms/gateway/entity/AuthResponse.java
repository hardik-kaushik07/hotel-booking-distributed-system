package com.ms.gateway.entity;

import lombok.*;

import java.util.Collection;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String user_id;
    private String access_token;
    private String refresh_token;
    private long expiry_date;

    private Collection<String> authories;
}
