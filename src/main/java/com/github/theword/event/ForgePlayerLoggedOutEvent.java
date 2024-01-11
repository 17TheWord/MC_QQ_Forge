package com.github.theword.event;

public final class ForgePlayerLoggedOutEvent extends ForgeNoticeEvent {
    public ForgePlayerLoggedOutEvent(ForgeServerPlayer player) {
        super("ForgePlayerLoggedOutEvent", "quit", player);
    }
}
