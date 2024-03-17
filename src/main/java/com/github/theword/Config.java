package com.github.theword;

import com.github.theword.constant.ConfigConstantMessage;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.theword.MCQQ.LOGGER;

@Getter
public class Config {
    private boolean enableMcQQ;
    private boolean enableChatMessage;
    private boolean enableReconnectMessage;
    private int reconnectMaxTimes;
    private int reconnectInterval;
    private List<String> websocketUrlList;
    private String sayWay;
    private boolean enableCommandMessage;
    private boolean enableDeathMessage;
    private boolean enableJoinMessage;
    private boolean enableQuitMessage;
    private String serverName;

    public Config(boolean isModServer) {
        String configFolder;
        if (isModServer) {
            configFolder = "mods";
        } else {
            configFolder = "plugins";
        }
        Path configMapFilePath = Paths.get("./" + configFolder, "MC_QQ", "config.yml");
        if (!Files.exists(configMapFilePath)) {
            LOGGER.info(ConfigConstantMessage.CONFIG_NOT_EXIST);
            try {
                InputStream inputStream = MCQQ.class.getClassLoader().getResourceAsStream("config.yml");
                assert inputStream != null;
                FileUtils.copyInputStreamToFile(inputStream, configMapFilePath.toFile());
            } catch (IOException e) {
                LOGGER.warn(ConfigConstantMessage.CONFIG_COPY_FIELD);
            }
        }
        try {
            Yaml yaml = new Yaml();
            Reader reader = Files.newBufferedReader(configMapFilePath);
            Map<String, Object> configMap = yaml.load(reader);
            enableMcQQ = (boolean) configMap.get("enable_mc_qq");
            enableChatMessage = (boolean) configMap.get("enable_chat_message");
            enableReconnectMessage = (boolean) configMap.get("enable_reconnect_msg");
            reconnectMaxTimes = (int) configMap.get("reconnect_max_times");
            reconnectInterval = (int) configMap.get("reconnect_interval");
            websocketUrlList = new ArrayList<>();
            try {
                Object websocketUrlListObject = configMap.get("websocket_url_list");
                if (websocketUrlListObject instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) websocketUrlListObject;
                    for (Object websocketUrl : list) {
                        if (websocketUrl instanceof String) {
                            websocketUrlList.add((String) websocketUrl);
                        } else {
                            LOGGER.warn(ConfigConstantMessage.CONFIG_NO_STRING_IN_WEBSOCKET_URL_LIST + websocketUrl);
                        }
                    }
                } else {
                    websocketUrlList = new ArrayList<String>() {{
                        add("ws://127.0.0.1:8080/minecraft/ws");
                    }};
                    LOGGER.warn(ConfigConstantMessage.CONFIG_WEBSOCKET_URL_LIST_ERROR_USE_DEFAULT);
                }
            } catch (ClassCastException e) {
                websocketUrlList = new ArrayList<String>() {{
                    add("ws://127.0.0.1:8080/minecraft/ws");
                }};
                LOGGER.warn(ConfigConstantMessage.CONFIG_WEBSOCKET_URL_LIST_ERROR_USE_DEFAULT);
            }
            sayWay = (String) configMap.get("say_way");
            enableCommandMessage = (boolean) configMap.get("enable_command_message");
            enableDeathMessage = (boolean) configMap.get("enable_death_message");
            enableJoinMessage = (boolean) configMap.get("enable_join_message");
            enableQuitMessage = (boolean) configMap.get("enable_quit_message");
            serverName = (String) configMap.get("server_name");
        } catch (Exception e) {
            LOGGER.warn(ConfigConstantMessage.CONFIG_READING_ERROR);
            enableMcQQ = true;
            enableChatMessage = true;
            enableReconnectMessage = false;
            reconnectMaxTimes = 5;
            reconnectInterval = 5000;
            websocketUrlList = new ArrayList<String>() {{
                add("ws://127.0.0.1:8080/minecraft/ws");
            }};
            sayWay = "说：";
            enableCommandMessage = false;
            enableDeathMessage = true;
            enableJoinMessage = true;
            enableQuitMessage = true;
            serverName = "Server";
        }
    }
}
