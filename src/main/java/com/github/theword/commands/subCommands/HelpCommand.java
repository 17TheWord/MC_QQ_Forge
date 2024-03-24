package com.github.theword.commands.subCommands;

import com.github.theword.commands.SubCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static com.github.theword.CommandTool.sendResultComponent;
import static com.github.theword.commands.CommandManager.subCommandList;

public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "mcqq";
    }

    @Override
    public String getDescription() {
        return "查看 MC_QQ 命令";
    }

    @Override
    public String getSyntax() {
        return "/mcqq";
    }

    @Override
    public String getUsage() {
        return "使用：/mcqq";
    }

    public HelpCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mcqq")
                .requires(source -> source.hasPermission(2))
                .executes(
                        context -> {
                            sendResultComponent(context, "-------------------");
                            for (SubCommand subCommand : subCommandList) {
                                sendResultComponent(context, subCommand.getUsage() + "---" + subCommand.getDescription());
                            }
                            sendResultComponent(context, "-------------------");
                            return Command.SINGLE_SUCCESS;
                        }
                )
        );

    }
}
