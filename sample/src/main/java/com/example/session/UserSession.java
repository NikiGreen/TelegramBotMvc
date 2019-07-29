package com.example.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
public class UserSession {

    private final ThreadLocal<Boolean> create = new ThreadLocal<>();
    private final ThreadLocal<Boolean> udate = new ThreadLocal<>();
    private final ThreadLocal<Boolean> delete = new ThreadLocal<>();

    public UserSession() {
    }

    public  void setCurrentUserCommandCreate(Boolean command) {
        create.set(command);
    }

    public  void setCurrentUserCommandcUpdate(Boolean command) {
        udate.set(command);
    }
    public  void setCurrentUserCommandcDelete(Boolean command) {
        delete.set(command);
    }

    public  Boolean getCurrentUserCommand() {
        return false;
    }

    public  Boolean getCreate() {
        return create.get();
    }

    public  Boolean getUdate() {
        return udate.get();
    }

    public  Boolean getDelete() {
        return delete.get();
    }

    public  ThreadLocal<Boolean> getRemove() {
        return delete;
    }
}
