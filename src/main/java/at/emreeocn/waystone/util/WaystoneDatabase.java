package at.emreeocn.waystone.util;

import at.emreeocn.waystone.model.Waystone;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneDatabase {

    private File file;
    private YamlConfiguration db;

    public WaystoneDatabase() throws IOException {
        file = new File("plugins/Waystone", "waystones.yml");
        if(!file.exists()) file.getParentFile().createNewFile();
        db = YamlConfiguration.loadConfiguration(file);
    }

    public void saveWaystone(Waystone waystone) {

    }

    public void loadWaystonesFromDB() {
    }

}
