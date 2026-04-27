package com.itred.selectivekeepinv;

import net.neoforged.neoforge.common.ModConfigSpec;

public class SKIConfig {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLE_GRAVESTONES = BUILDER
            .comment("Henkelmax's Gravestones: disable placing gravestones and/or granting obituaries for players with selectivekeepinventory enabled")
            .translation("selectivekeepinventory.config.disable_gravestones")
            .define("disableGravestones", false);


    static final ModConfigSpec COMMON_CONFIG = BUILDER.build();

}
