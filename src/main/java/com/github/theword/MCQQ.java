package com.github.theword;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Mod(MCQQ.MOD_ID)
public class MCQQ {

    public static final String MOD_ID = "mcqq";
    public static final String MOD_NAME = "MC_QQ";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    static WSClient wsClient;
    static Map<String, String> httpHeaders = new HashMap<>();
    static int connectTime;
    static boolean serverOpen;

    public MCQQ() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onStartup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
    }

    private void onStartup(final FMLCommonSetupEvent event) {
        LOGGER.info("正在读取配置文件...");
        httpHeaders.put("x-self-name", Utils.unicodeEncode(ConfigReader.config().get("server_name").toString()));
        connectTime = 0;
        serverOpen = true;
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("WebSocket Client 正在启动...");
        LOGGER.info("WebSocket URL: " + ConfigReader.config().get("websocket_url"));
        try {
            wsClient = new WSClient();
            wsClient.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
