package com.example.session;

import org.springframework.stereotype.Component;

@Component
public class UserSessionVer {
    static Boolean create=false;
    static Boolean update=false;
    static Boolean delete=false;

    public UserSessionVer() {
    }


    public static Boolean getCreate() {
        return create;
    }

    public static void setCreate(Boolean create) {
        UserSessionVer.create = create;
    }

    public static Boolean getUpdate() {
        return update;
    }

    public static void setUpdate(Boolean update) {
        UserSessionVer.update = update;
    }

    public static Boolean getDelete() {
        return delete;
    }

    public static void setDelete(Boolean delete) {
        UserSessionVer.delete = delete;
    }
}
