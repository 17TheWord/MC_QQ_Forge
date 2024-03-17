package com.github.theword;


import com.github.theword.models.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
            ForgePlayerLoggedInEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedInEvent(getPlayer((ServerPlayerEntity) event.getPlayer()));
            sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        if (config.isEnableQuitMessage() && !event.isCanceled()) {
            ForgePlayerLoggedOutEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedOutEvent(getPlayer((ServerPlayerEntity) event.getPlayer()));
            sendMessage(getEventJson(forgePlayerLoggedInEvent));
        }
    }

    @SubscribeEvent
    public void onPlayerCommand(CommandEvent event) {
        if (config.isEnableCommandMessage() && !event.isCanceled()) {
            if (event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayerEntity) {
                String command = event.getParseResults().getReader().getString();

                if (!command.startsWith("l ") && !command.startsWith("login ") && !command.startsWith("register ") && !command.startsWith("reg ") && !command.startsWith("mcqq ")) {
                    ForgeServerPlayer player = getPlayer((ServerPlayerEntity) Objects.requireNonNull(event.getParseResults().getContext().getSource().getEntity()));
                    ForgeCommandEvent forgeCommandEvent = new ForgeCommandEvent("", player, command);
                    sendMessage(getEventJson(forgeCommandEvent));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (config.isEnableDeathMessage() && !event.isCanceled()) {
            if (event.getEntity() instanceof ServerPlayerEntity) {
                ForgeServerPlayer player = getPlayer((ServerPlayerEntity) event.getEntity());
                ForgePlayerDeathEvent forgeCommandEvent = new ForgePlayerDeathEvent("", player, event.getSource().getLocalizedDeathMessage((LivingEntity) event.getEntity()).getString());
                sendMessage(getEventJson(forgeCommandEvent));
            }
        }
    }
}
