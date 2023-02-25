package io.papermc.testplugin;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.RegistryManager;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.event.RegistryAdditionEvent;
import io.papermc.paper.registry.event.RegistryPreFreezeEvent;
import io.papermc.paper.registry.event.listener.RegistryAdditionListener;
import io.papermc.paper.registry.event.listener.RegistryPreFreezeListener;
import io.papermc.paper.registry.keys.GameEventKeys;
import net.kyori.adventure.key.Key;
import org.bukkit.GameEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.NotNull;

@DefaultQualifier(NonNull.class)
public class TestPluginBootstrap implements PluginBootstrap {

    static final TypedKey<GameEvent> NEW_EVENT = TypedKey.create(RegistryKey.GAME_EVENT, Key.key("machine_maker", "best_event"));

    @Override
    public void bootstrap(@NotNull BootstrapContext context) {
        final RegistryManager manager = context.getRegistryManager();
        manager.registerPreFreezeListener(RegistryKey.GAME_EVENT, new Test());
        manager.registerAdditionListener(RegistryKey.GAME_EVENT, new Test2());
    }

    private static final class Test implements RegistryPreFreezeListener<GameEvent, GameEvent.Builder> {
        @Override
        public void beforeFreeze(final RegistryPreFreezeEvent<GameEvent, GameEvent.Builder> event) {
            event.registry().register(NEW_EVENT, builder -> {
                builder.range(2);
            });
        }
    }

    private static final class Test2 implements RegistryAdditionListener<GameEvent, GameEvent.Builder> {
        @Override
        public void beforeRegister(final RegistryAdditionEvent<GameEvent, GameEvent.Builder> event) {
            if (event.key().equals(GameEventKeys.BLOCK_OPEN)) {
                event.builder().range(event.builder().range() * 2);
            }
        }
    }
}
