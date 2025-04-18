package net.baldy;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoneEaterUtils {
    public static class UtilsPlayer {
        public String target_player_name = "UNKNOWN_PLAYER_NAME";
        public Player player = null;
        public Boolean is_self = false;
    }

    @SuppressWarnings("LoggerStringConcat")
    public static UtilsPlayer get_player(CommandSender sender, String[] args, Integer index)
    {   
        UtilsPlayer target = new UtilsPlayer();

        if (args.length > index) {
            target.target_player_name = args[index];
            target.player = Bukkit.getPlayer(target.target_player_name);
        }
        else {
            target.player = (Player)sender;
            target.is_self = true;
        }

        return target;
    }
}
