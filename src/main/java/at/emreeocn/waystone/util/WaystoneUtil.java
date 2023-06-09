package at.emreeocn.waystone.util;

import at.emreeocn.waystone.model.Waystone;

import java.util.HashSet;
import java.util.Set;

public class WaystoneUtil {

    private static Set<Waystone> waystones = new HashSet<>();

    public static void addWaystone(Waystone waystone) {
        waystones.add(waystone);
    }

    public static void removeWaystone(Waystone waystone) {
        waystones.remove(waystone);
    }

    public static Set<Waystone> getWaystones() {
        return waystones;
    }

}
