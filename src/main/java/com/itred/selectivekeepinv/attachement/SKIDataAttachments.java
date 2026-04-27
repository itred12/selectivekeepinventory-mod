package com.itred.selectivekeepinv.attachement;

import com.itred.selectivekeepinv.SelectiveKeepInventory;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class SKIDataAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, SelectiveKeepInventory.MODID);

    public static final Supplier<AttachmentType<Boolean>> KEEPINV_ATTACHMENT = ATTACHMENT_REGISTER.register(
            "individual_keep_inventory",
            () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .copyOnDeath()
                    .build()
    );

}
