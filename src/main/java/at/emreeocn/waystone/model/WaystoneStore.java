package at.emreeocn.waystone.model;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.data.WaystoneConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;

public class WaystoneStore {

    private UUID player;
    private Set<Waystone> waystones;

    public WaystoneStore(UUID player, Set<Waystone> waystones) {
        this.player = player;
        this.waystones = waystones;
    }

    public boolean addWaystone(Waystone waystone) {
        if (waystones.contains(waystone)) return false;
        if (waystones.stream().anyMatch(waystone1 -> waystone1.getName() != null && waystone1.getName().equalsIgnoreCase(waystone.getName())))
            return false;
        if(waystones.size() >= WaystoneConfig.getMaxWaystones()) return false;
        waystones.add(waystone);
        return true;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public Set<Waystone> getWaystones() {
        return waystones;
    }

    public void setWaystones(Set<Waystone> waystones) {
        this.waystones = waystones;
    }

    public boolean contains(Waystone waystone) {
        return waystones.contains(waystone);
    }
}
