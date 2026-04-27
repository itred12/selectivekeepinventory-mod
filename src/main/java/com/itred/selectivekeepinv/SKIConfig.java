package com.itred.selectivekeepinv;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SKIConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_GRAVESTONES = BUILDER
            .comment("Henkelmax's Gravestones: \n\nDisable placing gravestones and/or granting obituaries for players with selectivekeepinventory enabled")
            .translation("selectivekeepinventory.config.disable_gravestones")
            .define("disableGravestones", false);

    public static final ModConfigSpec.BooleanValue KEEPINVTAG_COMPAT = BUILDER
            .comment("Widder's KeepInvTag: \n\nAutomatically enable SelectiveKeepInventory for players with the \"KeepInv\" command tag when they join, for if you were using the KeepInvTag mod beforehand.")
            .translation("selectivekeepinventory.config.keepinvtag_compat")
            .define("keepInvTagCompat", false);

    static final ModConfigSpec COMMON_CONFIG = BUILDER.build();

}
