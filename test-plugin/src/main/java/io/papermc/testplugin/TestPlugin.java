package io.papermc.testplugin;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.VanillaArguments;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        CommandBuilder.of(this, "hello")
            .then(
                RequiredArgumentBuilder.argument("getreal", VanillaArguments.uuid())
            ).then(
                RequiredArgumentBuilder.<CommandSourceStack, BlockState>argument("getrealy", VanillaArguments.blockState()).executes((context) -> {
                    BlockState state = context.getArgument("getrealy", BlockState.class);
                    System.out.println(state);
                    return 1;
                })
            ).then(
                RequiredArgumentBuilder.<CommandSourceStack, Species>argument("species", new SpeciesArgument()).executes((context) -> {
                    Species species = context.getArgument("species", Species.class);
                    System.out.println(species);
                    return 1;
                }).then(RequiredArgumentBuilder.<CommandSourceStack, Integer>argument("temperature", new TemperatureArgument()).executes((context) -> {
                    int temp = context.getArgument("temperature", Integer.class);
                    Species species = context.getArgument("species", Species.class);
                    System.out.println("ITS %s degrees C for the %s".formatted(temp, species));
                    return 1;
                }))
            )
            .aliases("wow", "bob", "weird spaces", "oog")
            .register();

        this.getServer().getCommandMap().register("fallback", new BukkitCommand("hi", "cool hi command", "<>", List.of("hialias")) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                sender.sendMessage("hi");
                return true;
            }


        });
    }


}
