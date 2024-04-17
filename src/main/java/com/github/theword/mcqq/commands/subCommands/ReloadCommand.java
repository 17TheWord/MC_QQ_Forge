
package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.SubCommand;
import com.github.theword.mcqq.constant.CommandConstantMessage;
import com.github.theword.mcqq.constant.WebsocketConstantMessage;
import com.github.theword.mcqq.utils.Config;
import com.github.theword.mcqq.websocket.WsClient;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

import java.net.URI;
import java.net.URISyntaxException;

import static com.github.theword.mcqq.CommandTool.sendResultComponent;
import static com.github.theword.mcqq.utils.Tool.config;
import static com.github.theword.mcqq.utils.Tool.wsClientList;


public class ReloadCommand extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "重载配置文件并重新连接所有 Websocket Client";
    }

    @Override
    public String getSyntax() {
        return "/mcqq reload";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq reload";
    }


    public ReloadCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("mcqq").requires(source -> source.hasPermission(2)).then(Commands.literal("reload").executes(context -> {
            config = new Config(true);
            sendResultComponent(context, CommandConstantMessage.RELOAD_CONFIG);
            wsClientList.forEach(wsClient -> {
                if (!wsClient.isClosed() && !wsClient.isClosing()) {
                    wsClient.close();
                    sendResultComponent(context, String.format(CommandConstantMessage.RELOAD_CLOSE_WEBSOCKET_CLIENT, wsClient.getURI()));
                }
                wsClient.getTimer().cancel();
            });
            wsClientList.clear();
            sendResultComponent(context, CommandConstantMessage.RELOAD_CLEAR_WEBSOCKET_CLIENT_LIST);
            config.getWebsocketUrlList().forEach(websocketUrl -> {
                try {
                    WsClient wsClient = new WsClient(new URI(websocketUrl));
                    wsClient.connect();
                    wsClientList.add(wsClient);
                } catch (URISyntaxException e) {
                    sendResultComponent(context, String.format(WebsocketConstantMessage.WEBSOCKET_ERROR_URI_SYNTAX_ERROR, websocketUrl));
                }
            });
            sendResultComponent(context, CommandConstantMessage.RELOADED);
            return Command.SINGLE_SUCCESS;
        })));

    }

}
