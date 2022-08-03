package io.papermc.paper.testplugin;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import io.papermc.paper.command.brigadier.argument.WrapperArgumentType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.concurrent.CompletableFuture;

public class TemperatureArgument extends WrapperArgumentType<Integer, Integer> {

    public TemperatureArgument() {
        super(IntegerArgumentType.integer());
    }

    @Override
    public Integer convert(Integer baseType) throws CommandSyntaxException {
        return ((baseType - 32) * 5) / 9;
    }

}
