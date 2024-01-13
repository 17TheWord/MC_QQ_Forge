package com.github.theword;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.github.theword.MCQQ.LOGGER;

public class ConfigReader {
    public static Map<String, Object> configMap = new HashMap<>();

    public static void loadConfig() {
        Path configMapFilePath = Paths.get("./mods", "mcqq", "config.yml");
        if (!Files.exists(configMapFilePath)) {
            LOGGER.info("配置文件不存在，将自动生成");
            try {
                InputStream inputStream = MCQQ.class.getClassLoader().getResourceAsStream("config.yml");
                assert inputStream != null;
                FileUtils.copyInputStreamToFile(inputStream, configMapFilePath.toFile());
            } catch (IOException e) {
                LOGGER.error("生成配置文件失败");
            }
        }

        // 读取配置文件
        try {
            Yaml yaml = new Yaml();
            Reader reader = Files.newBufferedReader(configMapFilePath);
            configMap = yaml.load(reader);
            LOGGER.info("加载配置文件成功");
        } catch (IOException e) {
            LOGGER.info("读取配置文件失败，将采用默认值");
            configMap.put("enable_mc_qq", true);
            configMap.put("enable_reconnect_msg", true);
            configMap.put("websocket_url", "ws://127.0.0.1:8080/minecraft/ws");
            configMap.put("say_way", " 说：");
            configMap.put("command_message", false);
            configMap.put("death_message", true);
            configMap.put("join_quit", true);
            configMap.put("server_name", "Server");
            configMap.put("log_local", ".\\logs\\");
            configMap.put("log_name", "latest.log");
        }
    }
}
