package io.papermc.testplugin;

import io.papermc.paper.event.player.ChatEvent;
import io.papermc.paper.math.Position;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
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
        final Location loc = event.getPlayer().getLocation();
        loc.subtract(0, 2, 0);
        BiFunction<World, Location, Block> function = World::getBlockAt;
        System.out.println(function.apply(event.getPlayer().getWorld(), loc));

        final Chunk chunk = event.getPlayer().getWorld().getChunkAt(loc);
        System.out.println(chunk);

        final Location rod = event.getPlayer().getWorld().findLightningRod(loc);
        System.out.println(rod);
    }
}
