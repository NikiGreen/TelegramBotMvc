package com.system.session;

import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private static Boolean create = false;
    private static Boolean update = false;
    private static Boolean delete = false;

    public UserSession() {
    }


    public static Boolean getCreate() {
        return create;
    }

    public static void setCreate(Boolean create) {
        UserSession.create = create;
    }

    public static Boolean getUpdate() {
        return update;
    }

    public static void setUpdate(Boolean update) {
        UserSession.update = update;
    }

    public static Boolean getDelete() {
        return delete;
    }

    public static void setDelete(Boolean delete) {
        UserSession.delete = delete;
    }
}
