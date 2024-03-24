package com.github.theword.models.forge;

import com.github.theword.eventModels.base.BaseCommandEvent;

public class ForgeCommandEvent extends BaseCommandEvent {
    public ForgeCommandEvent(String messageId, ForgeServerPlayer player, String command) {
        super("ForgeCommandEvent", messageId, player, command);
    }
}
