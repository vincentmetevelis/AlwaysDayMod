package com.vincentmet.alwaysday;

import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(BaseClass.MODID)
public class BaseClass{
    public static final String MODID = "alwaysday";
    
    public BaseClass(){
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }
    
    @Mod.EventBusSubscriber(modid = BaseClass.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandler{
        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event){
            if(event.getWorld() instanceof ServerWorld){
                ((ServerWorld)event.getWorld()).getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, ((ServerWorld)event.getWorld()).getServer());
                ((ServerWorld)event.getWorld()).setDayTime(6000L);
            }
        }
    }
}