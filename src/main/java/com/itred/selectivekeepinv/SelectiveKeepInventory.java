package com.itred.selectivekeepinv;


import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.itred.selectivekeepinv.command.SKIEnableDisableCommand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@EventBusSubscriber(modid = SelectiveKeepInventory.MODID)
@Mod(value = SelectiveKeepInventory.MODID)
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

        modContainer.registerConfig(ModConfig.Type.COMMON, SKIConfig.COMMON_CONFIG);

    }


    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        SKIEnableDisableCommand.registerCommand(event.getDispatcher());
    }

    // Disable the player drops event entirely if the entity has the keep inventory attachment
    // (Is this a bad idea?)
    @SubscribeEvent
    public static void onPlayerDrops(LivingDropsEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
            // *May* be all I need for accessories compat???
            // Probably should have been doing this before lol
            // TODO: Test if this works over the somewhat intrusive mixins I've been using for Curios and Gravestones, I may switch.
            event.setCanceled(true);
        }
    }

    // Update players with the "KeepInv" command tag to automatically enable selectivekeepinventory for them– only if the config option is enabled
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Don't do this if they already have the data attachment, too
        if (SKIConfig.KEEPINVTAG_COMPAT.get() && event.getEntity().getTags().contains("KeepInv") && !event.getEntity().getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
            event.getEntity().setData(SKIDataAttachments.KEEPINV_ATTACHMENT, true);
            event.getEntity().sendSystemMessage(Component.translatable("message.selectivekeepinventory.autoenabled"));
        }
    }




}
