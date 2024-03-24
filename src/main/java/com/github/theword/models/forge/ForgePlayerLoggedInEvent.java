package com.github.theword.models.forge;

import com.github.theword.eventModels.base.BasePlayerJoinEvent;

public final class ForgePlayerLoggedInEvent extends BasePlayerJoinEvent {
    public ForgePlayerLoggedInEvent(ForgeServerPlayer player) {
        super("ForgePlayerLoggedInEvent", player);
    }
}
