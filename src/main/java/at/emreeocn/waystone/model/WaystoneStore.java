package at.emreeocn.waystone.model;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.util.WaystoneDatabase;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneStore {

    private UUID player;
    private Set<Waystone> waystones;

    public WaystoneStore(UUID player, Set<Waystone> waystones) {
        this.player = player;
        loadWaystones();
    }

    private void loadWaystones() {
        this.waystones = WaystonePlugin.getInstance().getDatabase().getWaystones(player);
    }

    public boolean addWaystone(Waystone waystone) {
        if (waystones.contains(waystone)) return false;
        if (waystones.stream().anyMatch(waystone1 -> waystone1.getName().equalsIgnoreCase(waystone.getName()))) return false;
        waystones.add(waystone);
        WaystonePlugin.getInstance().getDatabase().saveWaystone(waystone);
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
