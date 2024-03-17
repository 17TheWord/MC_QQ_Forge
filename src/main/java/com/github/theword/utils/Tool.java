package com.github.theword.utils;

import com.github.theword.constant.WebsocketConstantMessage;
import com.github.theword.models.ForgeEvent;
import com.github.theword.models.ForgeServerPlayer;
import com.google.gson.Gson;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

import static com.github.theword.MCQQ.*;

public class Tool {

    /**
     * 字符串转为 unicode 编码
     *
     * @param string 字符串
     * @return unicode编码
     */
    public static String unicodeEncode(String string) {
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
        forgeServerPlayer.setBlockX((int) player.getX());
        forgeServerPlayer.setBlockY((int) player.getY());
        forgeServerPlayer.setBlockZ((int) player.getZ());

        forgeServerPlayer.setSwimming(player.isSwimming());
        forgeServerPlayer.setSleeping(player.isSleeping());
        forgeServerPlayer.setBlocking(player.isBlocking());

        forgeServerPlayer.setFlying(player.isFallFlying());
        forgeServerPlayer.setFlyingSpeed(player.flyingSpeed);

        return forgeServerPlayer;
    }

    public static void sendMessage(String message) {
        if (config.isEnableMcQQ()) {
            wsClientList.forEach(
                    wsClient -> {
                        if (wsClient.isOpen()) {
                            wsClient.send(message);
                        } else {
                            LOGGER.info(String.format(WebsocketConstantMessage.WEBSOCKET_IS_NOT_OPEN_WHEN_SEND_MESSAGE, wsClient.getURI()));
                        }
                    }
            );
        }
    }

    public static void sendResultComponent(CommandContext<CommandSourceStack> context, String text) {
        context.getSource().sendSuccess(new TextComponent(text), false);
    }
}