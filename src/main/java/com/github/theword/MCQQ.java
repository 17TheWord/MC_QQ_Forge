package com.github.theword;

import com.github.theword.constant.WebsocketConstantMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Mod(MCQQ.MOD_ID)
public class MCQQ {

    public static final String MOD_ID = "mcqq";
    public static final String MOD_NAME = "MC_QQ";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static Config config = new Config(true);
    public static MinecraftServer minecraftServer;
    public static List<WsClient> wsClientList = new ArrayList<>();

    public MCQQ() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info(WebsocketConstantMessage.WEBSOCKET_RUNNING);
        minecraftServer = event.getServer();
        config.getWebsocketUrlList().forEach(url -> {
            try {
                WsClient wsClient = new WsClient(url);
                wsClient.connect();
                wsClientList.add(wsClient);
            } catch (URISyntaxException e) {
                LOGGER.warn(String.format(WebsocketConstantMessage.WEBSOCKET_ERROR_URI_SYNTAX_ERROR, url));
            }
        });
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent event) {
        wsClientList.forEach(
                wsClient -> {
                    wsClient.close(
                            1000,
                            String.format(WebsocketConstantMessage.WEBSOCKET_CLOSING, wsClient.getURI())
                    );
                    wsClient.getTimer().cancel();
                }
        );
    }
}
