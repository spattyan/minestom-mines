package com.yanspatt.listener;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import org.jetbrains.annotations.NotNull;

public interface GenericEventListener<T extends Event> {

    @NotNull EventListener<T> register();
}
