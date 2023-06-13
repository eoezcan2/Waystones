package at.emreeocn.namer;

import at.emreeocn.waystone.WaystonePlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class NamerDatabase {

    private final File file;
    private final YamlConfiguration db;

    public NamerDatabase() {
        try {
            if (!WaystonePlugin.getInstance().getDataFolder().exists())
                WaystonePlugin.getInstance().getDataFolder().mkdir();
            file = new File(WaystonePlugin.getInstance().getDataFolder(), "names.yml");
            if (!file.exists()) {
                file.createNewFile();
            }

            db = YamlConfiguration.loadConfiguration(file);

            loadYaml();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadYaml() {
        if(db.getKeys(false) == null) return;
        if(db.getKeys(false).isEmpty()) return;
        for (String key : db.getKeys(false)) {
            NamerAPI.addItemName(key);
        }
    }

    public void saveYaml() {
        for(String key : NamerAPI.getItemNames()) {
            db.set(key, true);
        }
        try {
            db.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromYaml(String name) {
        db.set(name, null);
    }

}
