package com.github.theword.models;

import com.google.gson.annotations.SerializedName;

import static com.github.theword.MCQQ.config;


public class ForgeEvent {
    @SerializedName("server_name")
    private final String serverName = config.getServerName();
    @SerializedName("event_name")
    private final String eventName;
    @SerializedName("post_type")
    private final String postType;
    @SerializedName("sub_type")
    private final String subType;
    private final int timestamp = (int) (System.currentTimeMillis() / 1000);

    public ForgeEvent(String eventName, String postType, String subType) {
        this.eventName = eventName;
        this.postType = postType;
        this.subType = subType;
    }
}
