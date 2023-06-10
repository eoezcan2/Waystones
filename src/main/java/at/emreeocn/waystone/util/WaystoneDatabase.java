package at.emreeocn.waystone.util;

import at.emreeocn.waystone.WaystonePlugin;
import at.emreeocn.waystone.model.Waystone;
import at.emreeocn.waystone.model.WaystoneStore;
import at.emreeocn.waystone.model.WaystoneStruct;
import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class WaystoneDatabase {

    private File file;
    private WaystoneStruct gson;

    public WaystoneDatabase() {
        try {
            if(!WaystonePlugin.getInstance().getDataFolder().exists()) WaystonePlugin.getInstance().getDataFolder().mkdir();
            file = new File(WaystonePlugin.getInstance().getDataFolder(), "waystones.json");
            if(!file.exists()) {
                file.createNewFile();
            }
            gson = loadGson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WaystoneStruct loadGson() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(file);
        return gson.fromJson(reader, WaystoneStruct.class);
    }

    public void saveGson() throws IOException {
        Writer writer = new FileWriter(file);
        Gson gson = new Gson();
        gson.toJson(this.gson, writer);
        writer.close();
    }

}
