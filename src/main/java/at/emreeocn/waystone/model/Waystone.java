package at.emreeocn.waystone.model;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.data.WaystoneConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Waystone implements Comparable<Waystone> {

    private UUID player;
    private String name;
    private Location location;
    private Set<Block> blocks;
    private Material icon;
    private boolean isPublic;

    public Waystone(UUID player, String name, Location location, boolean isPublic, Set<Block> blocks, Material icon) {
        this.player = player;
        this.name = name;
        this.location = location;
        this.blocks = new HashSet<>();
        this.isPublic = isPublic;
        this.blocks = blocks;
        this.icon = icon;
    }

    public Material getIcon() {
        return icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
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

    public Set<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(Set<Block> blocks) {
        this.blocks = blocks;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Waystone)) return false;
        Waystone other = (Waystone) o;
        if(other.getName() == null || this.getName() == null) return false;
        return other.getName().equals(this.getName()) &&
                other.getPlayer().toString().equals(this.getPlayer().toString());
    }

    public void destroy() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }

    public void build() throws IOException {
        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc1 = location.clone().add(0, 0, 0);
                loc1.getBlock().setType(WaystoneItem.MATERIALS[2]);
                blocks.add(loc1.getBlock());
            }
        }.runTaskLater(WaystonePlugin.getInstance(), 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc1 = location.clone().add(0, 1, 0);
                loc1.getBlock().setType(WaystoneItem.MATERIALS[1]);
                blocks.add(loc1.getBlock());
            }
        }.runTaskLater(WaystonePlugin.getInstance(), 10);

        new BukkitRunnable() {
            @Override
            public void run() {
                Location loc1 = location.clone().add(0, 2, 0);
                loc1.getBlock().setType(WaystoneItem.MATERIALS[0]);
                blocks.add(loc1.getBlock());
            }
        }.runTaskLater(WaystonePlugin.getInstance(), 10);

        // BUTTONS
        /*new BukkitRunnable() {
            @Override
            public void run() {
                Location loc1 = location.clone().add(-1, 0, 0);
                loc1.getBlock().setType(WaystoneItem.MATERIALS[3]);
                loc1.getBlock().setBlockData(WaystoneItem.MATERIALS[3].createBlockData("[face=west]"));
                blocks.add(loc1.getBlock());

                Location loc2 = location.clone().add(1, 0, 0);
                loc2.getBlock().setType(WaystoneItem.MATERIALS[3]);
                loc2.getBlock().setBlockData(WaystoneItem.MATERIALS[3].createBlockData("[face=east]"));
                blocks.add(loc2.getBlock());

                Location loc3 = location.clone().add(0, 0, -1);
                loc3.getBlock().setType(WaystoneItem.MATERIALS[3]);
                loc3.getBlock().setBlockData(WaystoneItem.MATERIALS[3].createBlockData("[face=north]"));
                blocks.add(loc3.getBlock());

                Location loc4 = location.clone().add(0, 0, 1);
                loc4.getBlock().setType(WaystoneItem.MATERIALS[3]);
                loc4.getBlock().setBlockData(WaystoneItem.MATERIALS[3].createBlockData("[face=south]"));
                blocks.add(loc4.getBlock());
            }
        }.runTaskLater(WaystonePlugin.getInstance(), 5);*/

        WaystonePlugin.getInstance().getWaystoneDatabase().save();
    }

    public void teleport(Player player) {
        // Generate random location in 1 block radius
        Location loc = location.clone();
        loc.setX(loc.getX() + Math.random() * 2 - 1);
        loc.setZ(loc.getZ() + Math.random() * 2 - 1);
        loc.setYaw(player.getLocation().getYaw());
        loc.setPitch(player.getLocation().getPitch());
        player.teleport(loc);
        particleCircle(loc, 1, 20);
    }

    private void particleCircle(Location loc, double radius, int amount) {
        for (int i = 0; i < amount; i++) {
            double angle = 2 * Math.PI * i / amount;
            Location particleLoc = loc.clone();
            particleLoc.setX(particleLoc.getX() + radius * Math.cos(angle));
            particleLoc.setZ(particleLoc.getZ() + radius * Math.sin(angle));
            particleLoc.getWorld().spawnParticle(Particle.valueOf(WaystoneConfig.getString("teleport-particle")), particleLoc, 1);
        }
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
