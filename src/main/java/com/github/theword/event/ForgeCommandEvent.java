package com.github.theword.event;

public class ForgeCommandEvent extends ForgeMessageEvent {
    public ForgeCommandEvent(String messageId, ForgeServerPlayer player, String command) {
        super("ForgeCommandEvent", "player_command", messageId, player, command);
    }
}
