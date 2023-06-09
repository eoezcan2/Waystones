package at.emreeocn.waystone.model;

import org.bukkit.Location;

import java.util.UUID;

public class Waystone {

    private UUID player;
    private Location location;
    private boolean isPublic;

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
