package com.itred.selectivekeepinv.command;

import com.itred.selectivekeepinv.attachement.SKIDataAttachments;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

// Mostly referenced from Gravestone's commands, since I couldn't find any real tutorials online for making commands in NeoForge.
public class SKIEnableDisableCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("keepinventoryfor")
                .requires((commandSourceStack -> commandSourceStack.hasPermission(2))); // 2 is "moderator" permissions

        // Enable logic
        Command<CommandSourceStack> enable = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");
            if (target.getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
                commandSource.getSource().sendSystemMessage(Component.translatable("message.selectivekeepinventory.alreadyenabled", target.getDisplayName()));
            } else {
                target.setData(SKIDataAttachments.KEEPINV_ATTACHMENT, true);
                commandSource.getSource().sendSuccess(() -> Component.translatable("message.selectivekeepinventory.enabledkeepinventory", target.getDisplayName()), true);
            }
            return 1;
        };

        // Disable logic
        Command<CommandSourceStack> disable = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");

            if (!target.getData(SKIDataAttachments.KEEPINV_ATTACHMENT)) {
                commandSource.getSource().sendSystemMessage(Component.translatable("message.selectivekeepinventory.alreadydisabled", target.getDisplayName()));
            } else {
                target.setData(SKIDataAttachments.KEEPINV_ATTACHMENT, false);
                commandSource.getSource().sendSuccess(() -> Component.translatable("message.selectivekeepinventory.disabledkeepinventory", target.getDisplayName()), true);
            }
            return 1;
        };

        // Check logic
        Command<CommandSourceStack> query = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");
            commandSource.getSource().sendSystemMessage(
                    Component.translatable(
                            "message.selectivekeepinventory.querykeepinventory",
                            target.getDisplayName(),
                            target.getData(SKIDataAttachments.KEEPINV_ATTACHMENT).toString()
                    )
            );
            return 1;
        };

        // Build the command
        literalBuilder
                .then(Commands.argument("target", EntityArgument.player()) // First take the target,
                        // Then either "enable", "disable", or "query", running the code attached to any one.
                        .then(Commands.literal("enable").executes(enable))
                        .then(Commands.literal("disable").executes(disable))
                        .then(Commands.literal("query").executes(query))
                );

        // Register the command
        LiteralCommandNode<CommandSourceStack> register = dispatcher.register(literalBuilder);

        // Create a similarly-named alias and register that too
        LiteralArgumentBuilder<CommandSourceStack> alias = Commands.literal("selectivekeepinventory")
                .requires(
                        commandSourceStack -> commandSourceStack.hasPermission(2)
                )
                .redirect(register);
        dispatcher.register(alias);
    }


}
