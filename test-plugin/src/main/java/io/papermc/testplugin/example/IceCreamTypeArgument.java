package io.papermc.testplugin.example;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class IceCreamTypeArgument extends CustomArgumentType.Converted<IceCreamType, String> {

    public IceCreamTypeArgument() {
        super(StringArgumentType.word());
    }

    @Override
    public @NotNull IceCreamType convert(String primitiveType) throws CommandSyntaxException {
        try {
            return IceCreamType.valueOf(primitiveType.toUpperCase());
        } catch (Exception e) {
            Message message = MessageComponentSerializer.message().serialize(Component.text("Invalid species %s!".formatted(primitiveType), NamedTextColor.RED));

            throw new CommandSyntaxException(new SimpleCommandExceptionType(message), message);
        }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        for (IceCreamType species : IceCreamType.values()) {
            builder.suggest(species.name(), MessageComponentSerializer.message().serialize(Component.text("COOL! TOOLTIP!", NamedTextColor.GREEN)));
        }

        return CompletableFuture.completedFuture(
            builder.build()
        );
    }
}
