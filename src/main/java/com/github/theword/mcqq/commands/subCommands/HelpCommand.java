package com.github.theword.mcqq.commands.subCommands;

import com.github.theword.mcqq.commands.CommandManager;
import com.github.theword.mcqq.commands.SubCommand;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static com.github.theword.mcqq.CommandTool.sendResultComponent;

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
                            for (SubCommand subCommand : CommandManager.subCommandList) {
                                sendResultComponent(context, subCommand.getUsage() + "---" + subCommand.getDescription());
                            }
                            sendResultComponent(context, "-------------------");
                            return Command.SINGLE_SUCCESS;
                        }
                )
        );

    }
}
