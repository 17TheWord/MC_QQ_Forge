package com.scareye.mcqq;

import com.google.gson.Gson;
import com.scareye.mcqq.event.ForgePlayerLoggedInEvent;
import com.scareye.mcqq.event.ForgePlayerLoggedOutEvent;
import com.scareye.mcqq.event.ForgeServerChatEvent;
import com.scareye.mcqq.event.ForgeServerPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.Event;

import static com.scareye.mcqq.ConfigReader.config;

public class Utils {

    static String processEventToJson(Event event) {
        String server_name = config().get("server_name").toString();
        Gson gson = new Gson();
        if (event instanceof ServerChatEvent) {
            String message = ((ServerChatEvent) event).getMessage().getString();
            ServerPlayer player = ((ServerChatEvent) event).getPlayer();
            ForgeServerChatEvent forgeServerChatEvent = new ForgeServerChatEvent(server_name, message, new ForgeServerPlayer(player.getName().getString(), player.getUUID().toString(), player.getIpAddress(), player.getLevel().toString(), player.getSpeed()));
            return gson.toJson(forgeServerChatEvent);
        } else if (event instanceof PlayerLoggedInEvent) {
            ServerPlayer player = (ServerPlayer) ((PlayerLoggedInEvent) event).getEntity();
            ForgePlayerLoggedInEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedInEvent(server_name, new ForgeServerPlayer(player.getName().getString(), player.getUUID().toString(), player.getIpAddress(), player.getLevel().toString(), player.getSpeed()));
            return gson.toJson(forgePlayerLoggedInEvent);
        } else if (event instanceof PlayerLoggedOutEvent) {
            ServerPlayer player = (ServerPlayer) ((PlayerLoggedOutEvent) event).getEntity();
            ForgePlayerLoggedOutEvent forgePlayerLoggedOutEvent = new ForgePlayerLoggedOutEvent(server_name, new ForgeServerPlayer(player.getName().getString(), player.getUUID().toString(), player.getIpAddress(), player.getLevel().toString(), player.getSpeed()));
            return gson.toJson(forgePlayerLoggedOutEvent);
        } else {
            return null;
        }
    }


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
}
