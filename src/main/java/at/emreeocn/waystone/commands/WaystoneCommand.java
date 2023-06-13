package at.emreeocn.waystone.commands;

import at.emreeocn.waystone.item.WaystoneItem;
import at.emreeocn.waystone.data.WaystoneAPI;
import at.emreeocn.waystone.data.WaystoneConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WaystoneCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (!player.hasPermission("waystone.*")) return false;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("get")) {
                player.getInventory().addItem(new WaystoneItem());
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {

                return true;
            }

            if (args[0].equalsIgnoreCase("stop")) {
                if (WaystoneAPI.getCreationMode().contains(player.getUniqueId())) {
                    WaystoneAPI.removePlayerFromCreation(player);
                    player.sendMessage(WaystoneConfig.getString("message-create-cancelled"));
                    return true;
                }
            }
        }

        return false;
    }
}
