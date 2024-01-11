package com.github.theword.event;

public final class ForgePlayerLoggedInEvent extends ForgeNoticeEvent {
    public ForgePlayerLoggedInEvent(ForgeServerPlayer player) {
        super("ForgePlayerLoggedInEvent", "join", player);
    }
}
