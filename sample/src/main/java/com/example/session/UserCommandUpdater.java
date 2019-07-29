package com.example.session;

import org.springframework.stereotype.Component;

@Component
public class UserCommandUpdater {
    public static void userSessionReset(Boolean point) {
        UserSession.setCreate(false);
        UserSession.setUpdate(false);
        UserSession.setDelete(false);
    }
}
