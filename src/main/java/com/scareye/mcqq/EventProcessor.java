package com.scareye.mcqq;


import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.scareye.mcqq.ConfigReader.config;
import static com.scareye.mcqq.MCQQ.wsClient;
import static com.scareye.mcqq.Utils.processEventToJson;

public class EventProcessor {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        if ((Boolean) config().get("enable_mc_qq") && !event.isCanceled()) {
            wsClient.sendMessage(processEventToJson(event));
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        if ((Boolean) config().get("join_quit") && !event.isCanceled()) {
            wsClient.sendMessage(processEventToJson(event));
        }
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        if ((Boolean) config().get("join_quit") && !event.isCanceled()) {
            wsClient.sendMessage(processEventToJson(event));
        }
    }
}
