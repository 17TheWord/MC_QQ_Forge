package com.github.theword.models.forge;

import com.github.theword.eventModels.base.BasePlayerQuitEvent;

public final class ForgePlayerLoggedOutEvent extends BasePlayerQuitEvent {
    public ForgePlayerLoggedOutEvent(ForgeServerPlayer player) {
        super("ForgePlayerLoggedOutEvent", player);
    }
}
