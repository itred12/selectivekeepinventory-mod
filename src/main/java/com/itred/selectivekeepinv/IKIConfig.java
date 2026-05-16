package com.itred.selectivekeepinv;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class IKIConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_GRAVESTONES = BUILDER
            .comment("Henkelmax's Gravestones: \n\nDisable placing gravestones and/or granting obituaries for players with individualkeepinventory enabled")
            .translation("individualkeepinventory.config.disable_gravestones")
            .define("disableGravestones", false);

    public static final ModConfigSpec.BooleanValue KEEPINVTAG_COMPAT = BUILDER
            .comment("Widder's KeepInvTag: \n\nAutomatically enable IndividualKeepInventory for players with the \"KeepInv\" command tag when they join, for if you were using the KeepInvTag mod beforehand.")
            .translation("individualkeepinventory.config.keepinvtag_compat")
            .define("keepInvTagCompat", false);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> PLAYER_LIST = BUILDER
            .comment("Players whose usernames are in this list will automatically have IndividualKeepInventory enabled for them on join (players already in the game must rejoin for this to take effect).")
            .translation("individualkeepinventory.config.playerlist")
            .defineListAllowEmpty("playerList", List.of(), () -> "", IKIConfig::validateList);


    private static boolean validateList(final Object obj) {
        return obj instanceof String;
    }

    static final ModConfigSpec COMMON_CONFIG = BUILDER.build();

    private static final ModConfigSpec.Builder STARTUP_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue TOGGLE_COMMAND = STARTUP_BUILDER
            .comment("Adds a command which all players can use to enable or disable IndvividualKeepInventory (only for themselves)")
            .translation("individualkeepinventory.config.toggle_command")
            .define("enableToggleCommand", false);


    static final ModConfigSpec STARTUP_CONFIG = STARTUP_BUILDER.build();




}
