package io.papermc.testplugin;

import io.papermc.paper.event.player.ChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        System.out.println(Bukkit.getServer().isOwnedByCurrentRegion(event.getPlayer().getWorld(), event.getPlayer().getLocation()));
        // final Location loc = event.getPlayer().getLocation();
        // loc.subtract(0, 2, 0);
        // BiFunction<World, Location, Block> function = World::getBlockAt;
        // System.out.println(function.apply(event.getPlayer().getWorld(), loc));
        //
        // final Chunk chunk = event.getPlayer().getWorld().getChunkAt(loc);
        // System.out.println(chunk);
        //
        // final Location rod = event.getPlayer().getWorld().findLightningRod(loc);
        // System.out.println(rod);
    }
}
