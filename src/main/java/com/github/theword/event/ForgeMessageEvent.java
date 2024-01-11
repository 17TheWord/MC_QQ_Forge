package com.github.theword.event;

import com.google.gson.annotations.SerializedName;

public class ForgeMessageEvent extends ForgeEvent {
    @SerializedName("message_id")
    private String messageId;
    private final ForgeServerPlayer player;
    private final String message;

    public ForgeMessageEvent(String eventName, String subType, String messageId, ForgeServerPlayer player, String message) {
        super(eventName, "message", subType);
        this.messageId = messageId;
        this.player = player;
        this.message = message;
    }
}
