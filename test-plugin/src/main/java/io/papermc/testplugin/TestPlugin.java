package io.papermc.testplugin;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.VanillaArguments;
import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import io.papermc.paper.testplugin.example.ExampleAdminCommand;
import io.papermc.paper.testplugin.example.IceCreamType;
import io.papermc.paper.testplugin.example.IceCreamTypeArgument;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// TODO: Move this to plugin bootstrapping
public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void reload(ServerResourcesReloadedEvent event) {
        ExampleAdminCommand.register(this);

        this.getServer().getCommandMap().register("fallback", new BukkitCommand("hi", "cool hi command", "<>", List.of("hialias")) {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                sender.sendMessage("hi");
                return true;
            }
        });
        this.getServer().getCommandMap().getKnownCommands().values().removeIf((command) -> {
            return command.getName().equals("hi");
        });
        // Occurs after commands can be reloaded, for now
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.updateCommands();
        }
    }


}
