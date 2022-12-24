package io.papermc.paper.testplugin;

import com.mojang.brigadier.Message;
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
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class SpeciesArgument extends WrapperArgumentType<Species, String> {

    public SpeciesArgument() {
        super(StringArgumentType.word());
    }

    @Override
    public @NotNull Species convert(String baseType) throws CommandSyntaxException {
        try {
            return Species.valueOf(baseType.toUpperCase());
        } catch (Exception e) {
            Message message = MessageComponentSerializer.message().serialize(Component.text("Invalid species %s!".formatted(baseType), NamedTextColor.RED));

            throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
        }
    }

    @Override
    public boolean handleSuggestions() {
        return true;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (Species species : Species.values()) {
            builder.suggest(species.name(), MessageComponentSerializer.message().serialize(Component.text("COOL! TOOLTIP!", NamedTextColor.GREEN)));
        }

        return CompletableFuture.completedFuture(
            builder.build()
        );
    }
}
