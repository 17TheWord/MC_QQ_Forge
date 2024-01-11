package com.github.theword;

import com.github.theword.event.ForgeEvent;
import com.github.theword.event.ForgeServerPlayer;
import com.google.gson.Gson;
import net.minecraft.server.level.ServerPlayer;

public class Utils {

    /**
     * 字符串转为 unicode 编码
     *
     * @param string 字符串
     * @return unicode编码
     */
    static String unicodeEncode(String string) {
        char[] utfBytes = string.toCharArray();
        StringBuilder unicodeBytes = new StringBuilder();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes.append("\\u").append(hexB);
        }
        return unicodeBytes.toString();
    }

    /**
     * 获取事件的 json 字符串
     *
     * @param event 事件
     * @return json 字符串
     */
    public static String getEventJson(ForgeEvent event) {
        Gson gson = new Gson();
        return gson.toJson(event);
    }


    /**
     * 获取 ForgeServerPlayer 对象
     *
     * @param player 服务器玩家
     * @return ForgeServerPlayer 对象
     */
    public static ForgeServerPlayer getPlayer(ServerPlayer player) {
        ForgeServerPlayer forgeServerPlayer = new ForgeServerPlayer();
        forgeServerPlayer.setNickname(player.getName().getString());
        forgeServerPlayer.setDisplayName(player.getDisplayName().getString());

        forgeServerPlayer.setUuid(player.getUUID().toString());
        forgeServerPlayer.setIpAddress(player.getIpAddress());
        forgeServerPlayer.setLevel(player.getLevel().toString());
        forgeServerPlayer.setSpeed(player.getSpeed());
        forgeServerPlayer.setGameMode(player.gameMode.getGameModeForPlayer().toString());
        forgeServerPlayer.setBlockX(player.getBlockX());
        forgeServerPlayer.setBlockY(player.getBlockY());
        forgeServerPlayer.setBlockZ(player.getBlockZ());

        forgeServerPlayer.setSwimming(player.isSwimming());
        forgeServerPlayer.setSleeping(player.isSleeping());
        forgeServerPlayer.setBlocking(player.isBlocking());

        forgeServerPlayer.setFlying(player.getAbilities().flying);
        forgeServerPlayer.setFlyingSpeed(player.getAbilities().getFlyingSpeed());

        return forgeServerPlayer;
    }
}
