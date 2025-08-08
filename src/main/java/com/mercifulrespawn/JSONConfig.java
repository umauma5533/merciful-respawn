package com.mercifulrespawn;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSONConfig {
    private static final String CONFIG_FILE_NAME = "merciful-respawn-config.json";
    private static final String CONFIG_DIR = "config";
    
    public static class ConfigData {
        public boolean enableMod = true;
        public int respawnRadius = 50;
        public int gracePeriodTicks = 6000;
        public String description = "Merciful Respawn MOD Configuration";
        
        public ConfigData() {}
        
        public ConfigData(boolean enableMod, int respawnRadius, int gracePeriodTicks) {
            this.enableMod = enableMod;
            this.respawnRadius = respawnRadius;
            this.gracePeriodTicks = gracePeriodTicks;
        }
    }
    
    public static void generateConfigFile() {
        try {
            // configディレクトリを作成
            Path configDir = Paths.get(CONFIG_DIR);
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }
            
            // 設定ファイルのパス
            Path configFile = configDir.resolve(CONFIG_FILE_NAME);
            
            // 既存のファイルが存在しない場合のみ生成
            if (!Files.exists(configFile)) {
                ConfigData defaultConfig = new ConfigData();
                
                // JSONファイルを生成
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonConfig = gson.toJson(defaultConfig);
                
                // ファイルに書き込み
                try (FileWriter writer = new FileWriter(configFile.toFile())) {
                    writer.write(jsonConfig);
                }
                
                System.out.println("Merciful Respawn MOD: Config file generated at " + configFile.toAbsolutePath());
            } else {
                System.out.println("Merciful Respawn MOD: Config file already exists at " + configFile.toAbsolutePath());
            }
            
        } catch (IOException e) {
            System.err.println("Merciful Respawn MOD: Failed to generate config file: " + e.getMessage());
        }
    }
    
    public static ConfigData loadConfig() {
        try {
            Path configFile = Paths.get(CONFIG_DIR, CONFIG_FILE_NAME);
            
            if (Files.exists(configFile)) {
                String jsonContent = Files.readString(configFile);
                Gson gson = new Gson();
                return gson.fromJson(jsonContent, ConfigData.class);
            } else {
                System.out.println("Merciful Respawn MOD: Config file not found, using default values");
                return new ConfigData();
            }
            
        } catch (IOException e) {
            System.err.println("Merciful Respawn MOD: Failed to load config file: " + e.getMessage());
            return new ConfigData();
        }
    }
    
    public static void saveConfig(ConfigData config) {
        try {
            Path configFile = Paths.get(CONFIG_DIR, CONFIG_FILE_NAME);
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonConfig = gson.toJson(config);
            
            try (FileWriter writer = new FileWriter(configFile.toFile())) {
                writer.write(jsonConfig);
            }
            
            System.out.println("Merciful Respawn MOD: Config saved to " + configFile.toAbsolutePath());
            
        } catch (IOException e) {
            System.err.println("Merciful Respawn MOD: Failed to save config file: " + e.getMessage());
        }
    }
}
