package com.github.theword;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;

public class CommandTool {
    public static void sendResultComponent(CommandContext<CommandSourceStack> context, String text) {
        context.getSource().sendSystemMessage(MutableComponent.create(new LiteralContents(text)));
    }
}
