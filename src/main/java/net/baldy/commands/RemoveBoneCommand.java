package net.baldy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.baldy.BoneEaterUtils;
import net.md_5.bungee.api.ChatColor;

public class RemoveBoneCommand implements CommandExecutor {
    public static ChatColor baldy_green = ChatColor.of("#00CB9B");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §r§cYou must be a puppy to use this command!");
            return true;
        }

        BoneEaterUtils.UtilsPlayer target = BoneEaterUtils.get_player(commandSender, args, 0);
        if (target.player == null) {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §r§cPlayer '" + baldy_green + target.target_player_name + "§c' not found!");
            return true;
        }


        
        

        return true;
    }
}
