package com.patizone.core_service.messaging;

import java.security.Principal;

public class WebSocketUser implements Principal {
    private final String email;

    public WebSocketUser(String email) {
        this.email = email;
    }
    @Override
    public String getName() {
        return email;
    }
}
