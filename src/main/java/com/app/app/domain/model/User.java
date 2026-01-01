package com.app.app.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    private String email;

    private String name;

    private String password;

    private boolean active;

    public boolean isEnabled() {
        return active;
    }
}
