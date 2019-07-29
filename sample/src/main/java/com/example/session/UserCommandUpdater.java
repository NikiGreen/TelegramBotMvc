package com.example.session;

import org.springframework.stereotype.Component;

@Component
public class UserCommandUpdater {
    public static void userSessionDelete(Boolean point) {
        UserSessionVer.setCreate(false);
        UserSessionVer.setUpdate(false);
        UserSessionVer.setDelete(false);
    }
}
