package org.union4dev.base.events.network;

import net.minecraft.network.Packet;
import org.union4dev.base.events.base.Event;

public class PacketReceiveEvent extends Event.EventCancellable {

    private final Packet<?> packet;

    public PacketReceiveEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
