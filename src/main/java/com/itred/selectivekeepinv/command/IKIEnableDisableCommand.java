package com.itred.selectivekeepinv.command;

import com.itred.selectivekeepinv.IKIConfig;
import com.itred.selectivekeepinv.attachement.IKIDataAttachments;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

// Mostly referenced from Gravestone's commands, since I couldn't find any real tutorials online for making commands in NeoForge.
public class IKIEnableDisableCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {


        if (IKIConfig.TOGGLE_COMMAND.get()) {

            // I know. I know this is so messy but I am SO tired right now you have no idea

            LiteralArgumentBuilder<CommandSourceStack> toggleCommandBuilder = Commands.literal("togglekeepinventory")
                    .executes(context -> {
                        Entity target = context.getSource().getEntity();
                        if (target == null) {
                            context.getSource().sendFailure(Component.literal("An unexpected error occured when executing that command: \"User doesn't exist??? ????\""));
                            return 0;
                        }

                        if (target.getData(IKIDataAttachments.KEEPINV_ATTACHMENT)) {
                            target.setData(IKIDataAttachments.KEEPINV_ATTACHMENT, false);
                            context.getSource().sendSystemMessage(Component.literal("Toggled IndividualKeepInventory to false"));
                        } else {
                            target.setData(IKIDataAttachments.KEEPINV_ATTACHMENT, true);
                            context.getSource().sendSystemMessage(Component.literal("Toggled IndividualKeepInventory to true"));
                        }
                        target.addTag("individualkeepinventory.hastoggled_ignorelistconfig_theyknowwhattheyredoin");
                        return 1;

                    })
                    .then(Commands.literal("query")
                    .executes(context -> {
                        Entity target = context.getSource().getEntity();
                        if (target == null) {
                            context.getSource().sendFailure(Component.literal("An unexpected error occured when executing that command: \"User doesn't exist??? ????\""));
                            return 0;
                        }

                        context.getSource().sendSystemMessage(
                                Component.literal(
                                        "IndividualKeepInventory for " +
                                                target.getDisplayName().getString() +
                                                " is set to: " + target.getData(IKIDataAttachments.KEEPINV_ATTACHMENT)
                                )

                        );
                        return 1;
                    }));

            dispatcher.register(toggleCommandBuilder);


        }

        LiteralArgumentBuilder<CommandSourceStack> literalBuilder = Commands.literal("keepinventoryfor")
                .requires((commandSourceStack -> commandSourceStack.hasPermission(
                        2
                ))); // 2 is "moderator" permissions

        // Enable logic
        Command<CommandSourceStack> enable = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");
            if (target.getData(IKIDataAttachments.KEEPINV_ATTACHMENT)) {
                commandSource.getSource().sendSystemMessage(Component.literal("IndividualKeepInventory was already enabled for " + target.getDisplayName().getString() + "!"));
            } else {
                target.setData(IKIDataAttachments.KEEPINV_ATTACHMENT, true);
                commandSource.getSource().sendSuccess(() -> Component.literal("IndividualKeepInventory has been enabled for " + target.getDisplayName().getString()), true);
            }
            return 1;
        };

        // Disable logic
        Command<CommandSourceStack> disable = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");

            if (!target.getData(IKIDataAttachments.KEEPINV_ATTACHMENT)) {
                commandSource.getSource().sendSystemMessage(Component.literal("IndividualKeepInventory was already disabled for " + target.getDisplayName().getString() + "!"));
            } else {
                target.setData(IKIDataAttachments.KEEPINV_ATTACHMENT, false);
                commandSource.getSource().sendSuccess(() -> Component.literal("IndividualKeepInventory has been disabled for " + target.getDisplayName().getString()), true);
            }
            return 1;
        };

        // Check logic
        Command<CommandSourceStack> query = (commandSource) -> {
            ServerPlayer target = EntityArgument.getPlayer(commandSource, "target");
            commandSource.getSource().sendSystemMessage(
                    Component.literal(
                            "IndividualKeepInventory for " +
                            target.getDisplayName().getString() +
                            " is set to: " + target.getData(IKIDataAttachments.KEEPINV_ATTACHMENT)
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
        LiteralArgumentBuilder<CommandSourceStack> alias = Commands.literal("individualkeepinventory")
                .requires(
                        commandSourceStack -> commandSourceStack.hasPermission(2)
                )
                .redirect(register);
        dispatcher.register(alias);
    }


}
