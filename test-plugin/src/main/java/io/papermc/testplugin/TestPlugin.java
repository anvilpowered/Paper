package io.papermc.testplugin;

import io.papermc.paper.event.player.ChatEvent;
import io.papermc.paper.event.world.StructuresLocateEvent;
import io.papermc.paper.math.Position;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.generator.structure.Structure;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        final StructuresLocateEvent.Result result = new StructuresLocateEvent.Result(event.getPlayer().getLocation(), Structure.FORTRESS);
        System.out.println(result.position());

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
