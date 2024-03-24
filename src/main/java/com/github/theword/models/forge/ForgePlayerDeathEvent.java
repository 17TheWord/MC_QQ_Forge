package com.github.theword.models.forge;

import com.github.theword.eventModels.base.BasePlayerDeathEvent;

public class ForgePlayerDeathEvent extends BasePlayerDeathEvent {

    public ForgePlayerDeathEvent(String messageId, ForgeServerPlayer player, String message) {
        super("ForgePlayerDeathEvent", messageId, player, message);
    }
}
