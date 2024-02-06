package com.github.theword;


import com.github.theword.event.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


import static com.github.theword.ConfigReader.configMap;
import static com.github.theword.MCQQ.LOGGER;
import static com.github.theword.MCQQ.wsClient;
import static com.github.theword.Utils.getEventJson;
import static com.github.theword.Utils.getPlayer;

public class EventProcessor {


    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        if (!event.isCanceled()) {
            ForgeServerChatEvent forgeServerChatEvent = new ForgeServerChatEvent("", getPlayer(event.getPlayer()), event.getMessage());
            wsClient.sendMessage(getEventJson(forgeServerChatEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        if ((Boolean) configMap.get("join_quit") && !event.isCanceled()) {
            ForgePlayerLoggedInEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedInEvent(getPlayer((ServerPlayer) event.getEntity()));
            wsClient.sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        if ((Boolean) configMap.get("join_quit") && !event.isCanceled()) {
            ForgePlayerLoggedOutEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedOutEvent(getPlayer((ServerPlayer) event.getEntity()));
            wsClient.sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerCommand(CommandEvent event) {
        if ((Boolean) configMap.get("command_message") && !event.isCanceled()) {
            System.out.println(event.getParseResults().getContext().getSource().getEntity());
            if (event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer) {
                String command = event.getParseResults().getReader().getString();

                if (!command.startsWith("l ") && !command.startsWith("login ") && !command.startsWith("register ") && !command.startsWith("reg ")) {
                    try {
                        ServerPlayer player = event.getParseResults().getContext().getSource().getPlayerOrException();
                        ForgeServerPlayer forgeServerPlayer = getPlayer(player);
                        command = command.replaceFirst("/", "");
                        ForgeCommandEvent forgeCommandEvent = new ForgeCommandEvent("", forgeServerPlayer, command);
                        wsClient.sendMessage(getEventJson(forgeCommandEvent));
                    } catch (CommandSyntaxException e) {
                        LOGGER.warn("处理命令事件获取玩家信息时发生异常" + e.getMessage());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if ((Boolean) configMap.get("death_message") && !event.isCanceled()) {
            if (event.getEntity() instanceof ServerPlayer) {
                ForgeServerPlayer player = getPlayer((ServerPlayer) event.getEntity());
                ForgePlayerDeathEvent forgeCommandEvent = new ForgePlayerDeathEvent("", player, event.getSource().getLocalizedDeathMessage(event.getEntityLiving()).getString());
                wsClient.sendMessage(getEventJson(forgeCommandEvent));
            }
        }
    }
}
