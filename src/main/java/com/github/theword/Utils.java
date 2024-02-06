package com.github.theword;

import com.github.theword.event.ForgeEvent;
import com.github.theword.event.ForgeServerPlayer;
import com.github.theword.returnBody.BaseReturnBody;
import com.github.theword.returnBody.MessageReturnBody;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import static com.github.theword.MCQQ.LOGGER;
import static com.github.theword.MCQQ.server;
import static com.github.theword.parse.ParseJsonToEvent.parseMessages;

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

    public static void parseWebSocketJson(String message) {
        // 组合消息
        Gson gson = new Gson();
        BaseReturnBody baseReturnBody = gson.fromJson(message, BaseReturnBody.class);
        JsonElement data = baseReturnBody.getData();
        switch (baseReturnBody.getApi()) {
            case "broadcast":
                MessageReturnBody messageList = gson.fromJson(data, MessageReturnBody.class);
                MutableComponent result = parseMessages(messageList.getMessageList());
                for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
                    serverPlayer.sendMessage(result, ChatType.SYSTEM, Util.NIL_UUID);
                }
                break;
            default:
                LOGGER.warn("未知的 API: " + baseReturnBody.getApi());
                break;
        }
    }
}
