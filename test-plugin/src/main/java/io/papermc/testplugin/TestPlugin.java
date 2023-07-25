package io.papermc.testplugin;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.argument.VanillaArgumentTypes;
import io.papermc.paper.event.server.ServerResourcesLoadEvent;
import io.papermc.testplugin.example.ExampleAdminCommand;
import io.papermc.testplugin.example.MaterialArgumentType;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getCommandMap().register("fallback", new BukkitCommand("hi", "cool hi command", "<>", List.of("hialias")) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                sender.sendMessage("hi");
                return true;
            }
        });
        this.getServer().getCommandMap().register("fallback", new BukkitCommand("cooler-command", "cool hi command", "<>", List.of("cooler-command-alias")) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                sender.sendMessage("hi");
                return true;
            }
        });
        this.getServer().getCommandMap().getKnownCommands().values().removeIf((command) -> {
            return command.getName().equals("hi");
        });
    }

    @EventHandler
    public void load(ServerResourcesLoadEvent event) {
        event.getCommands().register(this, Commands.literal("material")
            .then(Commands.literal("item")
                .then(Commands.argument("mat", MaterialArgumentType.item())
                    .executes(ctx -> {
                        ctx.getSource().getSender().sendPlainMessage(ctx.getArgument("mat", Material.class).name());
                        return Command.SINGLE_SUCCESS;
                    })
                )
            ).then(Commands.literal("block")
                .then(Commands.argument("mat", MaterialArgumentType.block())
                    .executes(ctx -> {
                        ctx.getSource().getSender().sendPlainMessage(ctx.getArgument("mat", Material.class).name());
                        return Command.SINGLE_SUCCESS;
                    })
                )
            )
            .build()
        );

        event.getCommands().register(this, Commands.literal("heya")
            .then(Commands.argument("range", VanillaArgumentTypes.doubleRange())
                .executes((ct) -> {
                    ct.getSource().getSender().sendPlainMessage(VanillaArgumentTypes.getDoubleRange(ct, "range").toString());
                    return 1;
                })
            ).build()
        );

        event.getCommands().register(this, Commands.literal("root_command")
            .then(Commands.literal("sub_command")
                .requires(source -> source.getSender().hasPermission("testplugin.test"))
                .executes(ctx -> {
                    ctx.getSource().getSender().sendPlainMessage("root_command sub_command");
                    return Command.SINGLE_SUCCESS;
                })).build()
        );

        event.getCommands().register(this, "example", "test", new BasicCommand() {
            @Override
            public int execute(@NotNull final CommandSourceStack commandSourceStack, final @NotNull String[] args) {
                System.out.println(Arrays.toString(args));
                return Command.SINGLE_SUCCESS;
            }

            @Override
            public @NotNull Collection<String> suggest(final @NotNull CommandSourceStack commandSourceStack, final @NotNull String[] args) {
                System.out.println(Arrays.toString(args));
                return List.of("apple", "banana");
            }
        });


        event.getCommands().register(this, Commands.literal("water")
            .requires(source -> {
                System.out.println("isInWater check");
                return source.getExecutor().isInWater();
            })
            .executes(ctx -> {
                ctx.getSource().getExecutor().sendMessage("You are in water!");
                return Command.SINGLE_SUCCESS;
            }).then(Commands.literal("lava")
                .requires(source -> {
                    System.out.println("isInLava check");
                    return source.getExecutor().isInLava();
                })
                .executes(ctx -> {
                    ctx.getSource().getExecutor().sendMessage("You are in lava!");
                    return Command.SINGLE_SUCCESS;
                })).build());


        ExampleAdminCommand.register(this, event.getCommands());
    }


}
