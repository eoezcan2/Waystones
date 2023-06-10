package at.emreeocn.waystone.model;

import org.bukkit.Location;

import java.util.UUID;

public class Waystone implements Comparable<Waystone> {

    private UUID player;
    private String name;
    private Location location;
    private boolean isPublic;

    public Waystone(UUID player, String name, Location location, boolean isPublic) {
        this.player = player;
        this.name = name;
        this.location = location;
        this.isPublic = isPublic;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Waystone)) return false;
        Waystone other = (Waystone) o;
        if (other.getName().equals(this.getName()) &&
                other.getPlayer().toString().equals(this.getPlayer().toString())) return true;
        return false;
    }

    @Override
    public int compareTo(Waystone o) {
        return 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.player != null ? this.player.hashCode() : 0);
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.location != null ? this.location.hashCode() : 0);
        return hash;
    }
}
