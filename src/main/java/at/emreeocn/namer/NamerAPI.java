package at.emreeocn.namer;

import at.emreeocn.waystone.WaystonePlugin;

import java.util.HashSet;
import java.util.Set;

public class NamerAPI {

    private static final Set<String> itemNames = new HashSet<String>();

    public static Set<String> getItemNames() {
        return itemNames;
    }

    public static void addItemName(String name) {
        itemNames.add(name);
    }

    public static void saveItemName(String name) {
        itemNames.add(name);
        WaystonePlugin.getInstance().getNamerDatabase().saveYaml();
    }

    public static boolean itemNameExists(String name) {
        return itemNames.contains(name);
    }

    public static void removeItemName(String name) {
        itemNames.remove(name);
        WaystonePlugin.getInstance().getNamerDatabase().removeFromYaml(name);
    }

}
