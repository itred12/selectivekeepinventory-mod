package com.itred.selectivekeepinv;


import com.itred.selectivekeepinv.attachement.IKIDataAttachments;
import com.itred.selectivekeepinv.command.IKIEnableDisableCommand;
import com.itred.selectivekeepinv.util.InventoryDropsManager;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@EventBusSubscriber(modid = IndividualKeepInventory.MODID)
@Mod(value = IndividualKeepInventory.MODID)
public class IndividualKeepInventory {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "individualkeepinventory";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String GRAVESTONE_CURIOS_ID = "gravestonecurioscompat";
    public static final String CURIOS_ID = "curios";



    public IndividualKeepInventory(IEventBus eventBus, ModContainer modContainer) {
        // NeoForge.EVENT_BUS.addListener(SelectiveKeepInventory::onPlayerDeath);

        modContainer.registerConfig(ModConfig.Type.STARTUP, IKIConfig.STARTUP_CONFIG);
        modContainer.registerConfig(ModConfig.Type.COMMON, IKIConfig.COMMON_CONFIG);

        IKIDataAttachments.ATTACHMENT_REGISTER.register(eventBus);

    }


    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        IKIEnableDisableCommand.registerCommand(event.getDispatcher());
    }


    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {

        if (!event.isWasDeath()) {
            return;
        }

        Player newBody = event.getEntity();
        Player oldBody = event.getOriginal();
        InventoryDropsManager.playerDeathHandler(oldBody, newBody);


    }

    // Update players with the "KeepInv" command tag to automatically enable individualkeepinventory for them– only if the config option is enabled
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        // Don't do this if they already have the data attachment, too
        if (IKIConfig.KEEPINVTAG_COMPAT.get() && event.getEntity().getTags().contains("KeepInv") && !event.getEntity().getData(IKIDataAttachments.KEEPINV_ATTACHMENT)) {
            event.getEntity().setData(IKIDataAttachments.KEEPINV_ATTACHMENT, true);
            event.getEntity().sendSystemMessage(Component.literal("[IndividualKeepInventory] \"KeepInv\" command tag was detected on your player, so individualkeepinventory has been automatically enabled (more details in mod config)."));
            return;
        }

        IndividualKeepInventory.LOGGER.info(IKIConfig.PLAYER_LIST.get().toString());

        if (!IKIConfig.PLAYER_LIST.get().isEmpty()) {
            Player player = event.getEntity();

            // IndividualKeepInventory.LOGGER.info(player.getName().getString());

            if (!player.getData(IKIDataAttachments.KEEPINV_ATTACHMENT) && IKIConfig.PLAYER_LIST.get().contains(player.getName().getString())) {
                if (player.getTags().contains("individualkeepinventory.hastoggled_ignorelistconfig_theyknowwhattheyredoin")) {
                    return;
                }

                event.getEntity().setData(IKIDataAttachments.KEEPINV_ATTACHMENT, true);
                event.getEntity().sendSystemMessage(Component.literal("[IndividualKeepInventory] IndividualKeepInventory was pre-enabled for you by a server moderator."));
            }
        }
    }




}
