package io.papermc.testplugin;

import io.papermc.paper.event.server.ServerResourcesReloadedEvent;
import io.papermc.testplugin.example.ExampleAdminCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        ExampleAdminCommand.register(this);
    }

    @EventHandler
    public void reload(ServerResourcesReloadedEvent event) {
        ExampleAdminCommand.register(this);

    }


}
