package me.miquiis.examplemod.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommand {

    public ModCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("examplemod")

        );
    }

}
