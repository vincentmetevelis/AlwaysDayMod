package com.vincentmet.alwaysday;

import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(BaseClass.MODID)
public class BaseClass{
    public static final String MODID = "alwaysday";
    
    public BaseClass(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
    }
    
    private void setupCommon(final FMLCommonSetupEvent event){
        Config.ReadWrite.readFromFile(FMLPaths.CONFIGDIR.get(), BaseClass.MODID + ".json");
        PacketHandler.init();
    }
    
    @Mod.EventBusSubscriber(modid = BaseClass.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandler{
        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event){
            if(event.getWorld() instanceof ServerWorld){
                ((ServerWorld)event.getWorld()).getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, ((ServerWorld)event.getWorld()).getServer());
                if(Config.SidedConfig.isAlwaysNight()){
                    ((ServerWorld)event.getWorld()).setDayTime(18000L);//setNightTime
                }else{
                    ((ServerWorld)event.getWorld()).setDayTime(6000L);//setDayTime
                }
            }
        }
    }
}