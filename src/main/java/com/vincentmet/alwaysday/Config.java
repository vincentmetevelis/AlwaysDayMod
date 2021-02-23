package com.vincentmet.alwaysday;

import com.google.gson.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import net.minecraftforge.fml.common.thread.EffectiveSide;

public class Config{
    public static class ReadWrite{
        public static void readFromFile(Path path, String file){
            JsonObject json = loadConfig(path, file);
            if(json.has("always_night") && json.get("always_night").isJsonPrimitive() && json.get("always_night").getAsJsonPrimitive().isBoolean()){
                ServerConfig.ALWAYS_NIGHT = json.get("always_night").getAsBoolean();
            }
        }
        
        private static JsonObject loadConfig(Path path, String filename){
            try {
                StringBuilder res = new StringBuilder();
                Files.readAllLines(path.resolve(filename), StandardCharsets.UTF_8).forEach(res::append);
                return new JsonParser().parse(res.toString()).getAsJsonObject();
            }catch (IOException e) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String out = gson.toJson(getDefaultConfigJson());
                writeTo(path, filename, out);
                return loadConfig(path, filename);
            }
        }
        
        private static JsonObject getDefaultConfigJson(){
            JsonObject json = new JsonObject();
            json.addProperty("__comment", "When set to true, it will be always night instead of always day!");
            json.addProperty("always_night", false);
            return json;
        }
        
        public static void writeTo(Path location, String filename, Object text){
            try{
                if(!location.toFile().exists()){
                    location.toFile().mkdirs();
                }
                Files.write(location.resolve(filename), text.toString().getBytes());
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public static class ServerConfig{
        public static boolean ALWAYS_NIGHT = false;
    }
    
    public static class ServerToClientSyncedConfig{
        public static boolean ALWAYS_NIGHT = false;
    }
    
    public static class SidedConfig{
        public static boolean isAlwaysNight(){
            return EffectiveSide.get().isClient() ? ServerToClientSyncedConfig.ALWAYS_NIGHT : ServerConfig.ALWAYS_NIGHT;
        }
    }
}