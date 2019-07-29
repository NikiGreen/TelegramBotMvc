package com.example.session;

import org.springframework.stereotype.Component;

@Component
public class UserSessionVer {
    Boolean create=false;
    Boolean update=false;
    Boolean delete=false;

    public UserSessionVer() {
    }



    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
}
