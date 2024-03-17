package com.github.theword;


import com.github.theword.models.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static com.github.theword.MCQQ.config;
import static com.github.theword.utils.Tool.*;

public class EventProcessor {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        if (config.isEnableChatMessage()) {
            ForgeServerChatEvent forgeServerChatEvent = new ForgeServerChatEvent("", getPlayer(event.getPlayer()), event.getMessage());
            sendMessage(getEventJson(forgeServerChatEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        if (config.isEnableJoinMessage() && !event.isCanceled()) {
            ForgePlayerLoggedInEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedInEvent(getPlayer((ServerPlayer) event.getPlayer()));
            sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        if (config.isEnableQuitMessage() && !event.isCanceled()) {
            ForgePlayerLoggedOutEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedOutEvent(getPlayer((ServerPlayer) event.getPlayer()));
            sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerCommand(CommandEvent event) {
        if (config.isEnableCommandMessage() && !event.isCanceled()) {
            if (event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer) {
                String command = event.getParseResults().getReader().getString();

                if (!command.startsWith("l ") && !command.startsWith("login ") && !command.startsWith("register ") && !command.startsWith("reg ") && !command.startsWith("mcqq ")) {
                    ForgeServerPlayer player = getPlayer((ServerPlayer) Objects.requireNonNull(event.getParseResults().getContext().getSource().getEntity()));
                    ForgeCommandEvent forgeCommandEvent = new ForgeCommandEvent("", player, command);
                    sendMessage(getEventJson(forgeCommandEvent));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (config.isEnableDeathMessage() && !event.isCanceled()) {
            if (event.getEntity() instanceof ServerPlayer) {
                ForgeServerPlayer player = getPlayer((ServerPlayer) event.getEntity());
                ForgePlayerDeathEvent forgeCommandEvent = new ForgePlayerDeathEvent("", player, event.getSource().getLocalizedDeathMessage((LivingEntity) event.getEntity()).getString());
                sendMessage(getEventJson(forgeCommandEvent));
            }
        }
    }
}