package com.example.session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope()
@Component
public class UserSession {
    private static final ThreadLocal<UserCommand> currentUserCommand = new ThreadLocal<>();

    public static void setCurrentUserCommand(UserCommand command) {
        currentUserCommand.set(command);
    }

    public static UserCommand getCurrentUserCommand() {
        return currentUserCommand.get();
    }
}
