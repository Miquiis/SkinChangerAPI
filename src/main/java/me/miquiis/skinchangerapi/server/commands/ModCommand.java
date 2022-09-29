package me.miquiis.skinchangerapi.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.miquiis.skinchangerapi.SkinChangerAPI;
import me.miquiis.skinchangerapi.common.SkinLocation;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.UUID;

public class ModCommand {

    public ModCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("skinchangerapi")
                .then(Commands.literal("load").then(Commands.argument("url", StringArgumentType.string()).executes(context -> {
                    ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
                    SkinChangerAPI.setPlayerSkin(serverPlayer, new SkinLocation(UUID.randomUUID().toString(), StringArgumentType.getString(context, "url")));
                    return 1;
                })))
                .then(Commands.literal("reset").executes(context -> {
                    ServerPlayerEntity serverPlayer = context.getSource().asPlayer();
                    SkinChangerAPI.clearPlayerSkin(serverPlayer);
                    return 1;
                }))
        );
    }

}
