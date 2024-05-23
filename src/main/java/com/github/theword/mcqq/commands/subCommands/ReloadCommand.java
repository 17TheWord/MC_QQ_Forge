package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.ForgeSubCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;

import static com.github.theword.mcqq.utils.Tool.websocketManager;


public class ReloadCommand extends ReloadCommandAbstract implements ForgeSubCommand {

    @Override
    public int onCommand(CommandContext<CommandSource> context) {
        websocketManager.reloadWebsocket(true, context);
        return Command.SINGLE_SUCCESS;
    }
}
