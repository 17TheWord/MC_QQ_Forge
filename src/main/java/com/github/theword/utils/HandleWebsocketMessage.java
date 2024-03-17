package com.github.theword.utils;

import com.github.theword.constant.WebsocketConstantMessage;
import com.github.theword.returnBody.BaseReturnBody;
import com.github.theword.returnBody.MessageReturnBody;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

import static com.github.theword.MCQQ.LOGGER;
import static com.github.theword.MCQQ.minecraftServer;

public class HandleWebsocketMessage {

    private final ParseJsonToEvent parseJsonToEvent = new ParseJsonToEvent();

    public void handleWebSocketJson(String message) {
        Gson gson = new Gson();
        BaseReturnBody baseReturnBody = gson.fromJson(message, BaseReturnBody.class);
        JsonElement data = baseReturnBody.getData();
        switch (baseReturnBody.getApi()) {
            case "broadcast":
                MessageReturnBody messageList = gson.fromJson(data, MessageReturnBody.class);
                StringTextComponent result = parseJsonToEvent.parseMessages(messageList.getMessageList());
                for (ServerPlayerEntity serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
                    serverPlayer.sendMessage(result, ChatType.SYSTEM, UUID.randomUUID());
                }
                break;
            default:
                LOGGER.warn(WebsocketConstantMessage.WEBSOCKET_UNKNOWN_API + baseReturnBody.getApi());
                break;
        }
    }

}
