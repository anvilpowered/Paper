package io.papermc.testplugin;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandBuilder;
import io.papermc.paper.command.brigadier.argument.BlockArgument;
import io.papermc.paper.command.brigadier.argument.EnchantmentArgument;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        CommandBuilder.of(this, "hello")
            .then(
                RequiredArgumentBuilder.argument("getreal", new EnchantmentArgument())
            ).then(
                RequiredArgumentBuilder.argument("getrealy", new BlockArgument())
            )
            .aliases("wow", "bob", "weird spaces", "oog")
            .register();
    }


}
