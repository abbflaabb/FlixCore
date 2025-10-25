package com.abbas.FlixCore.manager;

import lombok.Setter;

public class ChatLockManager {
    @Setter
    private static boolean locked = false;
    public static boolean isChatLocked() {
        return locked;
    }
    public static void togglechat() {
        locked = !locked;
    }
}
