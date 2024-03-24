package com.github.theword;


import com.github.theword.models.forge.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;

import static com.github.theword.utils.Tool.config;
import static com.github.theword.utils.Tool.sendMessage;

public class EventProcessor {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        if (config.isEnableChatMessage()) {
            ForgeServerChatEvent forgeServerChatEvent = new ForgeServerChatEvent("", getPlayer(event.getPlayer()), event.getMessage().getString());
            sendMessage(forgeServerChatEvent);
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        if (config.isEnableJoinMessage() && !event.isCanceled()) {
            ForgePlayerLoggedInEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedInEvent(getPlayer((ServerPlayer) event.getEntity()));
            sendMessage(forgePlayerLoggedInEvent);
        }
    }

    @SubscribeEvent
    public void onPlayerQuit(PlayerLoggedOutEvent event) {
        if (config.isEnableQuitMessage() && !event.isCanceled()) {
            ForgePlayerLoggedOutEvent forgePlayerLoggedInEvent = new ForgePlayerLoggedOutEvent(getPlayer((ServerPlayer) event.getEntity()));
            sendMessage(forgePlayerLoggedInEvent);
        }
    }

    @SubscribeEvent
    public void onPlayerCommand(CommandEvent event) {
        if (config.isEnableCommandMessage() && !event.isCanceled()) {
            if (event.getParseResults().getContext().getSource().isPlayer()) {
                String command = event.getParseResults().getReader().getString();

                if (!command.startsWith("l ") && !command.startsWith("login ") && !command.startsWith("register ") && !command.startsWith("reg ") && !command.startsWith("mcqq ")) {
                    ForgeServerPlayer player = getPlayer(Objects.requireNonNull(event.getParseResults().getContext().getSource().getPlayer()));
                    ForgeCommandEvent forgeCommandEvent = new ForgeCommandEvent("", player, command);
                    sendMessage(forgeCommandEvent);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (config.isEnableDeathMessage() && !event.isCanceled()) {
            if (event.getEntity() instanceof ServerPlayer) {
                ForgeServerPlayer player = getPlayer((ServerPlayer) event.getEntity());
                ForgePlayerDeathEvent forgeCommandEvent = new ForgePlayerDeathEvent("", player, event.getSource().getLocalizedDeathMessage(event.getEntity()).getString());
                sendMessage(forgeCommandEvent);
            }
        }
    }

    ForgeServerPlayer getPlayer(ServerPlayer player) {
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
