package com.github.theword.models;

public final class ForgeServerChatEvent extends ForgeMessageEvent {
    public ForgeServerChatEvent(String messageId, ForgeServerPlayer player, String message) {
        super("ForgeServerChatEvent", "chat", messageId, player, message);
    }

}
