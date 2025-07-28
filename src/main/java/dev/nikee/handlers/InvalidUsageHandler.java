package dev.nikee.handlers;

import dev.nikee.utils.TextUtil;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

public class InvalidUsageHandler
        implements dev.rollczi.litecommands.invalidusage.InvalidUsageHandler<CommandSender> {
    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        CommandSender sender = invocation.sender();
        Schematic schematic = result.getSchematic();
        if (schematic.isOnlyFirst()) {
            sender.sendMessage(TextUtil.format("&#FF0000☹ &cPoprawne u\u017cycie: &#FF0000" + schematic.first()));
            return;
        }
        sender.sendMessage(TextUtil.format("&#FF0000☹ &cPoprawne u\u017cycie:"));
        for (String scheme : schematic.all()) {
            sender.sendMessage(TextUtil.format("&8 - &#FF0000" + scheme));
        }
    }
}