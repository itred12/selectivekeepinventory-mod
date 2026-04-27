package com.itred.selectivekeepinv;


import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.itred.selectivekeepinv.command.SKIEnableDisableCommand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@EventBusSubscriber(modid = SelectiveKeepInventory.MODID)
@Mod(SelectiveKeepInventory.MODID)
public class SelectiveKeepInventory {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "selectivekeepinventory";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String GRAVESTONE_CURIOS_ID = "gravestonecurioscompat";
    public static final String CURIOS_ID = "curios";



    public SelectiveKeepInventory(IEventBus eventBus, ModContainer modContainer) {
        // NeoForge.EVENT_BUS.addListener(SelectiveKeepInventory::onPlayerDeath);

        SKIDataAttachments.ATTACHMENT_REGISTER.register(eventBus);

    }


    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        SKIEnableDisableCommand.registerCommand(event.getDispatcher());
    }




}
