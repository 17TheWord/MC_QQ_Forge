package com.github.theword.models;


public class ForgeNoticeEvent extends ForgeEvent {
    private final ForgeServerPlayer player;

    public ForgeNoticeEvent(String eventName, String subType, ForgeServerPlayer player) {
        super(eventName, "notice", subType);
        this.player = player;
    }

}
