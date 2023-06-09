package at.emreeocn.waystone.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneStore {

    private UUID player;
    private Set<Waystone> waystones;

    public WaystoneStore(UUID player) {
        this.player = player;
        loadWaystones();
    }

    private void loadWaystones() {
        this.waystones = new HashSet<>();
        // LOAD FROM YML

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
}
