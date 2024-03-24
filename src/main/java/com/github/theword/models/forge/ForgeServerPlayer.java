package com.github.theword.models.forge;

import com.github.theword.eventModels.base.BasePlayer;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ForgeServerPlayer extends BasePlayer {
    private String uuid;
    @SerializedName("display_name")
    private String displayName;

    @SerializedName("ip_address")
    private String ipAddress;

    private String level;

    private float speed;
    @SerializedName("flying_speed")
    private float flyingSpeed;

    @SerializedName("is_flying")
    private boolean isFlying;

    @SerializedName("is_swimming")
    private boolean isSwimming;
    @SerializedName("is_sleeping")
    private boolean isSleeping;
    @SerializedName("is_blocking")
    private boolean isBlocking;

    @SerializedName("game_mode")
    private String gameMode;

    @SerializedName("block_x")
    private int blockX;
    @SerializedName("block_y")
    private int blockY;
    @SerializedName("block_z")
    private int blockZ;
}
