package com.github.theword.commands.subCommands;

import com.github.theword.commands.SubCommand;
import com.github.theword.constant.CommandConstantMessage;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.theword.CommandTool.sendResultComponent;
import static com.github.theword.utils.Tool.wsClientList;

public class ReconnectCommand extends SubCommand {
    @Override
    public String getName() {
        return "reconnect";
    }

    @Override
    public String getDescription() {
        return "重新连接 Websocket Clients.";
    }

    @Override
    public String getSyntax() {
        return "/mcqq reconnect";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq reconnect [all]";
    }

    public ReconnectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mcqq").requires(source -> source.hasPermission(2)).then(Commands.literal("reconnect").executes(context -> {
            sendResultComponent(context, CommandConstantMessage.RECONNECT_NOT_OPEN_CLIENT);
            AtomicInteger opened = new AtomicInteger();
            wsClientList.forEach(wsClient -> {
                if (!wsClient.isOpen()) {
                    wsClient.reconnectWebsocket();
                    sendResultComponent(context, CommandConstantMessage.RECONNECT_MESSAGE.formatted(wsClient.getURI()));
                } else {
                    opened.getAndIncrement();
                }
            });
            if (opened.get() == wsClientList.size()) {
                sendResultComponent(context, CommandConstantMessage.RECONNECT_NO_CLIENT_NEED_RECONNECT);
            }
            sendResultComponent(context, CommandConstantMessage.RECONNECTED);
            return Command.SINGLE_SUCCESS;
        }).then(Commands.literal("all").executes(context -> {
            sendResultComponent(context, CommandConstantMessage.RECONNECT_ALL_CLIENT);
            wsClientList.forEach(wsClient -> {
                wsClient.reconnectWebsocket();
                sendResultComponent(context, CommandConstantMessage.RECONNECT_MESSAGE.formatted(wsClient.getURI()));
            });
            sendResultComponent(context, CommandConstantMessage.RECONNECTED);
            return Command.SINGLE_SUCCESS;
        }))));
    }


}
