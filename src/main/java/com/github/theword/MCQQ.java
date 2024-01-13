package com.github.theword;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.github.theword.ConfigReader.configMap;
import static com.github.theword.ConfigReader.loadConfig;
import static com.github.theword.Utils.unicodeEncode;

@Mod(MCQQ.MOD_ID)
public class MCQQ {

    public static final String MOD_ID = "mcqq";
    public static final String MOD_NAME = "MC_QQ";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    static WSClient wsClient;
    static Map<String, String> httpHeaders = new HashMap<>();
    static int connectTime;
    static boolean serverOpen;

    static MinecraftServer server;

    public MCQQ() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        loadConfig();
        connectTime = 0;
        serverOpen = true;
        httpHeaders.put("x-self-name", unicodeEncode(configMap.get("server_name").toString()));
        LOGGER.info("WebSocket Client 正在启动...");
        LOGGER.info("WebSocket URL: " + configMap.get("websocket_url"));
        server = event.getServer();
        try {
            wsClient = new WSClient();
            wsClient.connect();
        } catch (URISyntaxException e) {
            LOGGER.error("WebSocket URL 格式错误，请检查配置文件。");
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        LOGGER.info("WebSocket Client 正在关闭...");
        serverOpen = false;
        wsClient.close();
    }
}
